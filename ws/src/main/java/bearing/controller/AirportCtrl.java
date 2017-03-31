package bearing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bearing.data.bean.Airport;
import bearing.dto.DistanceAndBearing;
import bearing.services.ServiceAirport;

/**
 * Created by thibault.delambilly on 03/06/2015.
 */
@RestController
@RequestMapping("/ws/airport")
public class AirportCtrl
{
	@Autowired
	ServiceAirport serviceAirport;



	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Airport> getAllAirports() {

		return serviceAirport.findAll();
	}

	@RequestMapping(value = "/id/{airportId", method = RequestMethod.GET)
	public Airport getAirport(@PathVariable long airportId, Model model) {

		return serviceAirport.findById(airportId);
	}

	@RequestMapping(value = "/icao/{aCode:[a-zA-Z]{4}}", method = RequestMethod.GET)
	public Airport getAirportByIcao(@PathVariable String aCode, Model model) {

		return serviceAirport.findByIcao(aCode);
	}


	@RequestMapping(value = "/iata/{aCode:[a-zA-Z]{3}}", method = RequestMethod.GET)
	public Airport getAirportByIata(@PathVariable String aCode, Model model) {

		return serviceAirport.getAirportByIata(aCode);
	}

	@RequestMapping(value = "/countries", method = RequestMethod.GET)
	public List<String> getCountries() {

		return serviceAirport.getCountries();
	}

	@RequestMapping(value = "/search/{code}", method = RequestMethod.GET)
	public List<Airport> getAllAirports(@PathVariable String code, Model model) {

		return serviceAirport.searchIcaoOrIata(code);
	}

	@RequestMapping(value = "/distance/{codeType}/{departure}/{arrival}", method = RequestMethod.GET)
	public int getDistance(@PathVariable String codeType, @PathVariable String departure, @PathVariable String arrival, Model model) {

		return serviceAirport.getDistance(codeType, departure, arrival);
	}

	@RequestMapping(value = "/distanceAndBearing/{codeType}/{departure}/{arrival}", method = RequestMethod.GET)
	public DistanceAndBearing getDistanceAndBearing(@PathVariable String codeType, @PathVariable String departure, @PathVariable String arrival, Model model)
	{
		return serviceAirport.getDistanceAnBearing(codeType, departure, arrival);
	}
}
