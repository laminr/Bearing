package bearing.data.bean;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Thibault de Lambilly on 16/06/2015.
 */
@Entity
@Table(name = "route", uniqueConstraints= @UniqueConstraint(columnNames = {"departure_id", "arrival_id"}))
@Getter
@Setter
public class Route
{

	@EmbeddedId
	RouteId routeId;

	@OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
	List<Leg> legs;

	public Route() {};

	public Route(RouteId routeId) {
		this.routeId = routeId;
	};

	public int distance;
}

