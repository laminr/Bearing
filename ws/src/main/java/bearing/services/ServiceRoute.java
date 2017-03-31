package bearing.services;

import java.util.List;

import bearing.dto.PointAndBearing;

/**
 * Created by Thibault de Lambilly on 11/06/2015.
 */
public interface ServiceRoute
{
	List<PointAndBearing> getRoute(String departureCode, String arrivalCode);
}
