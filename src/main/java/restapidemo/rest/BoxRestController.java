package restapidemo.rest;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static restapidemo.rest.RestChecker.checkForbiddenAttribute;
import static restapidemo.rest.RestChecker.checkFound;
import static restapidemo.rest.RestChecker.checkState;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	public Collection<Box> listBoxes() {
		return boxRepository.list();
	}

	@RequestMapping(method = POST)
	public ResponseEntity<Void> addBox(@RequestBody Box box) {
		checkForbiddenAttribute(box.getId(),
			"ID is assigned automatically and MUST NOT be provided");
		Box newBox = boxRepository.save(box);

		return ResponseEntity.created(
			ServletUriComponentsBuilder.fromCurrentRequest()
				.pathSegment(newBox.getId().toString())
				.build()
				.toUri())
			.build();
	}

	@RequestMapping(value = "/{id}", method = GET)
	public Box readBox(@PathVariable("id") Long id) {
		return checkFound(
			boxRepository.find(id));
	}

	@RequestMapping(value = "/{id}", method = DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBox(@PathVariable("id") Long id) {
		Box box = checkFound(boxRepository.find(id));
		checkState(box.items().isEmpty(), "Box MUST be empty but currently is not");
		boxRepository.delete(id);
	}
}
