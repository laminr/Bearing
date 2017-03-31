package bearing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Thibault de Lambilly on 29/05/2015.
 */
@Controller
public class HomeCtrl {


	@RequestMapping("/")
	String index() {
		return "index";
	}

	@RequestMapping("properties")
	@ResponseBody
	java.util.Properties properties() {
		return System.getProperties();
	}
}
