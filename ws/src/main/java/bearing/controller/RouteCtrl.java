package bearing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bearing.dto.DistanceAndBearing;
import bearing.dto.PointAndBearing;
import bearing.services.ServiceAirport;
import bearing.services.ServiceRoute;

/**
 * Created by thibault.delambilly on 03/06/2015.
 */
@RestController
@RequestMapping("/ws/route")
public class RouteCtrl
{
	@Autowired
	ServiceRoute serviceRoute;

	@Autowired
	ServiceAirport serviceAirport;

	@RequestMapping(value = "/get/{departure}/{arrival}", method = RequestMethod.GET)
	public List<PointAndBearing> getRoute(@PathVariable String departure, @PathVariable String arrival, Model model) {

		return serviceRoute.getRoute(departure, arrival);
	}

	@RequestMapping(value = "/distance/{departure}/{arrival}", method = RequestMethod.GET)
	public DistanceAndBearing getDistance(@PathVariable String departure, @PathVariable String arrival, Model model) {

		return serviceAirport.getDistanceAnBearing("icao", departure, arrival);
	}
}
