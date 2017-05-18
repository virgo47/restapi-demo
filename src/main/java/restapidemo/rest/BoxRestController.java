package restapidemo.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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

	@RequestMapping(method = POST)
	public ResponseEntity<Void> list(@RequestBody Box box) {
		Box newBox = boxRepository.save(box);

		return ResponseEntity.created(
			ServletUriComponentsBuilder.fromCurrentRequest()
				.pathSegment(newBox.getId().toString())
				.build()
				.toUri())
			.build();
	}
}
