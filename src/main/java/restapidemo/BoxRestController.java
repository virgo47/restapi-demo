package restapidemo;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restapidemo.datalayer.BoxRepository;
import restapidemo.entities.Box;

@RestController
@RequestMapping(value = "/boxes")
public class BoxRestController {

	private final BoxRepository boxRepository;

	@Autowired
	public BoxRestController(BoxRepository boxRepository) {
		this.boxRepository = boxRepository;
	}

	@RequestMapping(method = GET)
	public Collection<Box> list() {
		return boxRepository.list();
	}
}
