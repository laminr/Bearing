package bearing.tools;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Thibault de Lambilly on 10/06/2015.
 */
public class Wgs84
{
	private static final double R = 6372.8; // In kilometers
	private static final int RADIUS = 6371;

	// http://rosettacode.org/wiki/Haversine_formula#Java
	public static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {

		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));

		return R * c;
	}

	/**
	 * Returns the (initial) bearing from point to destination point.
	 * @example
	 *     int p1 = new LatLon(52.205, 0.119), p2 = new LatLon(48.857, 2.351);
	 *     int b1 = p1.bearingTo(p2); // b1.toFixed(1): 156.2
	 */
	public static int bearingTo(double lat1, double lon1, double lat2, double lon2) {

		double φ1 = Math.toRadians(lat1);
		double φ2 = Math.toRadians(lat2);

		double Δλ = Math.toRadians(lon2 - lon1);

		double y = Math.sin(Δλ) * Math.cos(φ2);
		double x = Math.cos(φ1) * Math.sin(φ2) - Math.sin(φ1)* Math.cos(φ2) * Math.cos(Δλ);

		double theta = Math.atan2(y, x);

		return  (int)((Math.toDegrees(theta) + 360) % 360);
	}

	/**
	 * Returns final bearing arriving at destination destination point from point; the final bearing
	 * will differ from the initial bearing by varying degrees according to distance and latitude.
	 *
	 * @param   {LatLon} point - Latitude/longitude of destination point.
	 * @returns {number} Final bearing in degrees from north.
	 *
	 * @example
	 *     int p1 = new Point.double(52.205, 0.119), p2 = new Point.double(48.857, 2.351);
	 *     int b2 = p1.finalBearingTo(p2); // p2.toFixed(1): 157.9
	 */
	 public static int finalBearingTo (double lat1, double lon1, double lat2, double lon2) {
		// get initial bearing from destination point to this point & reverse it by adding 180°
		return (bearingTo(lat1, lon1, lat2, lon2)+180) % 360;
	}


	/**
	 * Returns the midpoint between 'this' point and the supplied point.
	 *
	 * @param   {LatLon} point - Latitude/longitude of destination point.
	 * @returns {LatLon} Midpoint between this point and the supplied point.
	 *
	 * @example
	 *     Point.double p1 = new Point.double(52.205, 0.119), p2 = new Point.double(48.857, 2.351);
	 *     Point.double pMid = p1.midpointTo(p2); // pMid.toString(): 50.5363°N, 001.2746°E
	 */
	public static final Point.Double midpointTo (double lat1, double lon1, double lat2, double lon2) {
		// see http://mathforum.org/library/drmath/view/51822.html for derivation

		double φ1 = Math.toRadians(lat1);
		double λ1 = Math.toRadians(lon1);
		double φ2 = Math.toRadians(lat2);
		double Δλ = Math.toRadians(lon2 - lon1);

		double Bx = Math.cos(φ2) * Math.cos(Δλ);
		double By = Math.cos(φ2) * Math.sin(Δλ);

		double φ3 = Math.atan2(Math.sin(φ1) + Math.sin(φ2), Math.sqrt((Math.cos(φ1)+Bx) * (Math.cos(φ1)+Bx) + By * By) );

		double λ3 = λ1 + Math.atan2(By, Math.cos(φ1) + Bx);
		λ3 = (λ3 + 3 * Math.PI) % (2 * Math.PI) - Math.PI; // normalise to -180..+180°

		return new Point.Double(Math.toDegrees(λ3), Math.toDegrees(φ3));
	}

	/**
	 * Returns the destination point from 'this' point having travelled the given distance on the
	 * given initial bearing (bearing normally varies around path followed).
	 *
	 * @param   {number} brng - Initial bearing in degrees.
	 * @param   {number} dist - Distance in km (on sphere of 'this' radius).
	 * @returns {LatLon} Destination point.
	 *
	 * @example
	 *     var p1 = new LatLon(51.4778, -0.0015);
	 *     var p2 = p1.destinationPoint(300.7, 7.794); // p2.toString(): 51.5135°N, 000.0983°W
	 */
	public static Point.Double destinationPoint(double lat, double lon, int bearing, int distance) {
		// see http://williams.best.vwh.net/avform.htm#LL

		double θ = Math.toRadians(bearing);
		double δ = distance / RADIUS; // angular distance in radians

		double φ1 = Math.toRadians(lat);
		double λ1 = Math.toRadians(lon);

		double φ2 = Math.asin( Math.sin(φ1)*Math.cos(δ) + Math.cos(φ1)*Math.sin(δ)*Math.cos(θ) );
		double λ2 = λ1 + Math.atan2(Math.sin(θ)*Math.sin(δ)*Math.cos(φ1), Math.cos(δ)-Math.sin(φ1)*Math.sin(φ2));

		λ2 = (λ2 + 3 * Math.PI) % (2 * Math.PI) - Math.PI; // normalise to -180..+180°

		return new Point2D.Double(Math.toDegrees(φ2), Math.toDegrees(λ2));
	}

}
