package bearing.data.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Thibault de Lambilly on 16/06/2015.
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class RouteId implements Serializable
{
	@Column(name = "departure_id")
	int departureId;

	@Column(name = "arrival_id")
	int arrivalId;

	public RouteId() {}
}
