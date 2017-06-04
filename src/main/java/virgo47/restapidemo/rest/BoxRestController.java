package virgo47.restapidemo.rest;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static virgo47.restapidemo.rest.ControllerUtils.REQUESTED_SUCCESS_CODE;

import virgo47.restapidemo.datalayer.BoxRepository;
import virgo47.restapidemo.entities.Box;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
		RestChecker.checkForbiddenAttribute(box.getId(),
			"ID is assigned automatically and MUST NOT be provided");
		Box newBox = boxRepository.save(box);

		return ControllerUtils.createdResponse(newBox);
	}

	// we use descriptive names of parameters, not just "id", but "boxId"
	@RequestMapping(value = "/{boxId}", method = GET)
	public Box readBox(@PathVariable("boxId") Long boxId) {
		return RestChecker.checkFound(
			boxRepository.find(boxId));
	}

	@RequestMapping(value = "/{boxId}", method = PUT)
	public ResponseEntity<?> updateBox(
		@PathVariable("boxId") Long boxId,
		@RequestParam(value = REQUESTED_SUCCESS_CODE, required = false) Integer successCode,
		@RequestBody Box box)
	{
		RestChecker.checkUpdatedIdMatch(boxId, box);
		Box updatedBox = boxRepository.save(box);

		return ControllerUtils.updateResponse(successCode, updatedBox);
	}

	@RequestMapping(value = "/{boxId}", method = DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBox(@PathVariable("boxId") Long boxId) {
		Box box = RestChecker.checkFound(boxRepository.find(boxId));
		RestChecker.checkState(box.items().isEmpty(), "Box MUST be empty but currently is not");
		boxRepository.delete(boxId);
	}

	@RequestMapping(value = "/{boxId}/empty", method = POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void emptyBox(@PathVariable("boxId") Long boxId) {
		Box box = RestChecker.checkFound(boxRepository.find(boxId));
		// beware of concurrency/transactions in real life systems
		box.items().forEach(it -> it.box = null);
		box.items().clear();
	}
}
