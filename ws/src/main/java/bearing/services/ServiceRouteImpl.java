package bearing.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bearing.business.RouteBusiness;
import bearing.data.bean.Airport;
import bearing.data.bean.Leg;
import bearing.data.bean.Route;
import bearing.data.bean.RouteId;
import bearing.data.repository.AirportRepository;
import bearing.data.repository.RouteRepository;
import bearing.dto.PointAndBearing;

/**
 * Created by Thibault de Lambilly on 11/06/2015.
 */
@Service
public class ServiceRouteImpl implements ServiceRoute
{

	@Autowired
	AirportRepository airportRepository;

	@Autowired
	RouteRepository routeRepository;

	@Override
	public List<PointAndBearing> getRoute(final String departureCode, final String arrivalCode)
	{

		List<PointAndBearing> flight = new ArrayList<PointAndBearing>();

		Airport departure = airportRepository.findByIcao(departureCode);
		Airport arrival = airportRepository.findByIcao(arrivalCode);

		RouteId routeId = new RouteId(departure.getId(), arrival.getId());

		Route route = routeRepository.findByRouteId(routeId);

		// not known route
		if (route == null) {
			flight = RouteBusiness.getRoute(departure, arrival);
			route = new Route(routeId);

			List<Leg> legs = new ArrayList<Leg>();
			for(PointAndBearing point : flight) {
				legs.add(new Leg(route, point));
			}

			route.setLegs(legs);

			routeRepository.save(route);

		// already known route
		} else  {

			for(Leg leg : route.getLegs()) {
				flight.add(leg.getPointAndBearing());
			}
		}

		return flight;
	}
}
