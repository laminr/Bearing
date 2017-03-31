package bearing.dto;

import java.awt.*;
import java.awt.geom.Point2D;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Thibault de Lambilly on 11/06/2015.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class PointAndBearing
{
	private Point.Double point = new Point2D.Double(0.0, 0.0);
	private int bearing = 360;

	public PointAndBearing() {};

	public PointAndBearing(double longitude, double latitude, int bearing) {
		this.point.setLocation(longitude, latitude);
		this.bearing = bearing;
	};

	@Override
	public boolean equals(Object that) {

		if ( !(that instanceof PointAndBearing) ) return false;

		PointAndBearing aThat = (PointAndBearing) that;

		if(this.getPoint().getX() == aThat.getPoint().getX()
			&& this.getPoint().getY() == aThat.getPoint().getY()) {
			return true;
		}
		else return false;
	}

}
