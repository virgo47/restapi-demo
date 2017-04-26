package restapidemo;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExperimentalRestController {

	@RequestMapping(value = "/all")
	public Map all() {
		Map<Object, Object> result = new HashMap<>();
		return result;
	}

	@RequestMapping(value = "/get", method = GET)
	public String get(@RequestParam(name = "zipcode") String zipcode) {
		return "OK-get" + zipcode;
	}
}
