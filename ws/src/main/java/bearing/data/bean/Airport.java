package bearing.data.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Thibault de Lambilly on 29/05/2015.
 */
@Entity
@Table(name = "airport")
@Getter
@Setter
public class Airport
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	private String city;

	private String country;

	private String iata;

	private String icao;

	private double latitude;

	private double longitude;

	private int altitude;

	@Column(name = "timezone")
	private double timeZone;

	private String type;

}
