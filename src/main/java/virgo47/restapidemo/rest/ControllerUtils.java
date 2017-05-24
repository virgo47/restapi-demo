package virgo47.restapidemo.rest;

import virgo47.restapidemo.entities.Entity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** @noinspection WeakerAccess */
public class ControllerUtils {

	public static ResponseEntity<Void> createdResponse(Entity entity) {
		return ResponseEntity.created(
			ServletUriComponentsBuilder.fromCurrentRequest()
				.pathSegment(entity.getId().toString())
				.build()
				.toUri())
			.build();
	}
}
