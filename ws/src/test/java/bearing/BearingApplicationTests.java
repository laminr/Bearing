package bearing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import bearing.services.ServiceAirport;
import bearing.services.ServiceRoute;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BearingApplication.class)
@WebAppConfiguration
public class BearingApplicationTests {

	@Autowired
	ServiceAirport serviceAirport;

	@Autowired
	ServiceRoute serviceRoute;

	@Test
	public void contextLoads() {
	}

	@Test
	public void createRoutes() {

/*		List<Airport> airports =  serviceAirport.findByCountry("United States");

		for (Airport airport : airports) {
			if (!"".equals(airport.getIcao()) && airport.getIcao().length() == 4) {
				serviceRoute.getRoute("LFRS", airport.getIcao());
			}
		}*/
	}
}
