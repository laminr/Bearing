package bearing.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import bearing.data.bean.Airport;
import bearing.data.bean.Route;

/**
 * Created by Thibault de Lambilly on 29/05/2015.
 */
@Transactional
public interface AirportRepository  extends CrudRepository<Airport, Long>
{
	// http://docs.spring.io/spring-data/jpa/docs/1.8.0.RELEASE/reference/html/

	List<Airport> findAll();

	List<Airport> findByCountry(String country);

	Airport findById(long id);

	@Query("SELECT a FROM Airport a WHERE a.iata = :iata")
	Airport findByIata(@Param("iata")String iataCode);

	Airport findByIcao(String icaoCode);

	@Query("SELECT DISTINCT a.country FROM Airport a ORDER BY a.country ASC")
	List<String> findAirportCountries();

	@Query("SELECT a FROM Airport a WHERE UPPER(a.iata) LIKE CONCAT('%', :code, '%') OR a.icao LIKE CONCAT('%', :code, '%')")
	List<Airport> searchIcaoOrIata(@Param("code") String code);

	@Query("SELECT a FROM Airport a WHERE a.icao LIKE CONCAT(:code, '%')")
	List<Airport> findByIacoRegionCode(@Param("code") String code);
}
