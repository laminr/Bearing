package bearing.data.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import bearing.dto.PointAndBearing;

/**
 * Created by Thibault de Lambilly on 16/06/2015.
 */
@Entity
@Table(name = "leg")
@Getter
@Setter
public class Leg
{

	@Id
	@GeneratedValue
	int id;

	@ManyToOne()
	@JoinColumns({
		@JoinColumn(name="departure_id", referencedColumnName = "departure_id"),
		@JoinColumn(name="arrival_id", referencedColumnName = "arrival_id")
	})
	Route route;

	double latitude;

	double longitude;

	int bearing = 0;

	public Leg() {}

	public Leg(Route route, PointAndBearing point) {
		this(point);
		this.route = route;
	}

	public Leg(PointAndBearing point) {
		this.latitude = point.getPoint().getY();
		this.longitude = point.getPoint().getX();
		this.bearing = (int) point.getBearing();
	}

	public PointAndBearing getPointAndBearing() {
		return new PointAndBearing(longitude, latitude, bearing);
	}

}
