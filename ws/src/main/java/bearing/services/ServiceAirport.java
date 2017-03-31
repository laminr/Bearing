package bearing.services;

import java.util.List;

import bearing.data.bean.Airport;
import bearing.dto.DistanceAndBearing;

/**
 * Created by Thibault de Lambilly on 02/06/2015.
 */
public interface ServiceAirport
{
	List<Airport> findAll();

	List<Airport> findByIacoRegionCode(String regionCode);

	List<Airport> findByCountry(String country);

	Airport findById(long id);

	Airport getAirportByIata(String aCode);

	Airport findByIcao(String icao);

	List<String> getCountries();

	List<Airport> searchIcaoOrIata(String code);

	int getDistance(String codeType, String departureCode, String arrivalCode);

	DistanceAndBearing getDistanceAnBearing(String codeType, String departureCode, String arrivalCode);
}
