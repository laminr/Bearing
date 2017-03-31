package bearing.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bearing.business.AirportBusiness;
import bearing.data.bean.Airport;
import bearing.data.repository.AirportRepository;
import bearing.dto.DistanceAndBearing;
import bearing.tools.Wgs84;

/**
 * Created by Thibault de Lambilly on 02/06/2015.
 */
@Service
@Transactional
public class ServiceAirportImpl implements ServiceAirport
{
	@Autowired
	AirportRepository airportRepository;

	@Override
	public List<Airport> findAll()
	{
		return airportRepository.findAll();
	}

	@Override
	public List<Airport> findByIacoRegionCode(String regionCode)
	{
		return airportRepository.findByIacoRegionCode(regionCode);
	}

	@Override
	public List<Airport> findByCountry(final String country)
	{
		return airportRepository.findByCountry(country);
	}

	@Override
	public Airport findById(final long id)
	{
		return airportRepository.findById(id);
	}

	@Override
	public Airport getAirportByIata(final String iataCode)
	{
		return airportRepository.findByIata(iataCode.toUpperCase());
	}

	@Override
	public Airport findByIcao(final String icaoCode) {
		return airportRepository.findByIcao(icaoCode.toUpperCase());
	}

	@Override
	public List<String> getCountries() {
		return airportRepository.findAirportCountries();
	}

	@Override
	public List<Airport> searchIcaoOrIata(String code) {
		return airportRepository.searchIcaoOrIata(code.toUpperCase());
	}

	@Override
	public int getDistance(final String codeType, final String departureCode, final String arrivalCode)
	{
		int distance = 0;
		Airport departure = null;
		Airport arrival = null;

		if ("iata".equals(codeType)) {
			departure = airportRepository.findByIata(departureCode);
			arrival = airportRepository.findByIata(arrivalCode);

		} else if ("icao".equals(codeType)) {
			departure = airportRepository.findByIcao(departureCode);
			arrival = airportRepository.findByIcao(arrivalCode);
		}

		if (departure != null && arrival != null) {
			distance = AirportBusiness.calculateDistance(departure, arrival);
		}

		return distance;
	}

	@Override
	public DistanceAndBearing getDistanceAnBearing(String codeType, String departureCode, String arrivalCode)
	{
		DistanceAndBearing distanceAndBearing = new DistanceAndBearing();
		distanceAndBearing.setDistance(getDistance(codeType, departureCode, arrivalCode));

		Airport departure = null;
		Airport arrival = null;

		if ("iata".equals(codeType)) {
			departure = airportRepository.findByIata(departureCode);
			arrival = airportRepository.findByIata(arrivalCode);

		} else if ("icao".equals(codeType)) {
			departure = airportRepository.findByIcao(departureCode);
			arrival = airportRepository.findByIcao(arrivalCode);
		}

		if (departure != null && arrival != null) {
			int bearing = Wgs84.bearingTo(departure.getLatitude(), departure.getLongitude(), arrival.getLatitude(), arrival.getLongitude());
			distanceAndBearing.setBearing(bearing);
		}

		return distanceAndBearing;
	}
}
