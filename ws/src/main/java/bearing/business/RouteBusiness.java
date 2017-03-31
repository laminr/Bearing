package bearing.business;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import bearing.data.bean.Airport;
import bearing.dto.PointAndBearing;
import bearing.tools.Wgs84;

/**
 * Created by Thibault de Lambilly on 10/06/2015.
 */
@Slf4j
public class RouteBusiness
{
	/*
	http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?dir=%2Fgfs.2015063006
	http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs.pl?file=gfs.t00z.pgrbf00.grib2&lev_10_m_above_ground=on&var_UGRD=on&var_VGRD=on&dir=%2Fgfs.2015063000
	http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?file=gfs.t06z.pgrb2.1p00.anl&all_lev=on&leftlon=0&rightlon=360&toplat=90&bottomlat=-90&dir=%2Fgfs.2015063006
	*/

	private final static int MAX_DISTANCE = 10;
	private final static int EARTH_DIAMETER_NM = 21600;
	private final static int MAX_CYCLE = (int)(EARTH_DIAMETER_NM / MAX_DISTANCE);

	public static List<PointAndBearing> getRoute(Airport departure, Airport arrival) {

		List<Point2D.Double> points = findAllPoint(departure, arrival);
		List<PointAndBearing> points3D = getBearing(points);
		cleanEqualsPoint(points3D);

		return points3D;
	}

	public static List<PointAndBearing> getBearing(List<Point2D.Double> points) {

		List<PointAndBearing> points3D = new ArrayList<PointAndBearing>();

		for (int i = 0; i < points.size()-1; i++) {
			Point.Double from = points.get(i);
			Point.Double to = points.get(i+1);

			int bearing = Wgs84.bearingTo(from.getY(), from.getX(), to.getY(), to.getX());
			points3D.add(new PointAndBearing(from.getX(), from.getY(), bearing));
		}

		// add the last point
		Point.Double last = points.get(points.size()-1);
		points3D.add(new PointAndBearing(last.getX(), last.getY(), 0));

		return points3D;
	}

	public static void cleanEqualsPoint(List<PointAndBearing> points) {

		List<PointAndBearing> toRemove = new ArrayList<PointAndBearing>();

		// find those to remove
		for (int i = 0; i < points.size()-1; i++) {
			PointAndBearing from = points.get(i);
			PointAndBearing to = points.get(i+1);

			if (from.getBearing() == to.getBearing() && i < points.size()-1) {
				toRemove.add(to);
			}
		}

		// remove them
		for (PointAndBearing point : toRemove) {
			points.removeAll(toRemove);
		}
	}

	public static List<Point2D.Double> findAllPoint(Airport departure, Airport arrival) {
		List<Point2D.Double> route = new ArrayList<Point2D.Double>();

		Point2D.Double dep = new Point2D.Double(departure.getLongitude(), departure.getLatitude());
		Point2D.Double arr = new Point2D.Double(arrival.getLongitude(), arrival.getLatitude());

		route.add(dep);
		route.add(arr);

		log.debug("Route : Start find all point");
		int position = -1;
		int count = 0;
		do {
			position = checkRouteComplet(route);
			if (position > -1) {
				try {
					addMiddlePoint(route, position);
				} catch (Exception e) {
					e.printStackTrace();
					position = -1;
				}
			}

			if (count++ > MAX_CYCLE) break;
		} while (position > -1);

		log.debug("Route : End find all point:"+route.size()+" points");
		return route;
	}

	private static void addMiddlePoint(final List<Point2D.Double> route, final int position) throws Exception{

		if (position+1 == route.size()) {
			throw new Exception("Wrong position");
		}

		Point2D.Double from = route.get(position);
		Point2D.Double to = route.get(position+1);

		Point.Double middle = Wgs84.midpointTo(from.getY(), from.getX(), to.getY(), to.getX());

		if (middle != null) {
			Point2D.Double point = new Point2D.Double();
			point.setLocation(middle.getX(), middle.getY());

			if (route.get(position).equals(point)) {
				throw new Exception("Identical Point");
			}

			route.add(position + 1, point);
			log.debug("Add Middel point {} / {}", point.getY(), point.getX());
		}
	}

	private static int checkRouteComplet(List<Point2D.Double> route) {

		int position = -1;

		for (int i = 0; i < route.size()-1; i++) {

			Point2D.Double from = route.get(i);
			Point2D.Double to = route.get(i+1);

			int distance = (int) Math.round(
				Wgs84.haversineDistance(from.getY(), from.getX(), to.getY(), to.getX())
			);

			log.debug("Distance --> {} NM", distance);

			if (distance > MAX_DISTANCE) {
				position = i;
				log.debug("Route not done!");
				break;
			}
		}

		return position;
	}
}
