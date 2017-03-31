package bearing.business;

import bearing.data.bean.Airport;
import bearing.tools.Wgs84;

/**
 * Created by Thibault de Lambilly on 10/06/2015.
 */
public class AirportBusiness
{
	public static int calculateDistance(Airport departure, Airport arrival) {

		return (int) Math.round(
			Wgs84.haversineDistance(
				departure.getLatitude(),
				departure.getLongitude(),
				arrival.getLatitude(),
				arrival.getLongitude()
			)
		);
	}

}
