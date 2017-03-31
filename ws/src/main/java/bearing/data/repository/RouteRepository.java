package bearing.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import bearing.data.bean.Route;
import bearing.data.bean.RouteId;

/**
 * Created by Thibault de Lambilly on 29/05/2015.
 */
@Transactional
public interface RouteRepository extends CrudRepository<Route, Long> {
	// http://docs.spring.io/spring-data/jpa/docs/1.8.0.RELEASE/reference/html/

	List<Route> findAll();

	Route findByRouteId(RouteId id);



}
