package virgo47.restapidemo.rest;

import virgo47.restapidemo.entities.Entity;

import org.springframework.http.HttpStatus;
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

	public static final String REQUESTED_SUCCESS_CODE = "successCode";

	public static ResponseEntity<?> updateResponse(
		Integer requestedSuccessCode, Entity entity)
	{
		if (requestedSuccessCode == null || requestedSuccessCode == HttpStatus.OK.value()) {
			return ResponseEntity.ok(entity);
		} else if (requestedSuccessCode == HttpStatus.NO_CONTENT.value()) {
			return ResponseEntity.noContent().build();
		} else if (requestedSuccessCode == HttpStatus.SEE_OTHER.value()) {
			return ResponseEntity.status(HttpStatus.SEE_OTHER)
				.location(
					ServletUriComponentsBuilder.fromCurrentRequest()
						.replaceQuery("")
						.build().toUri())
				.build();
		} else {
			throw RestChecker.invalidParameterValueException(REQUESTED_SUCCESS_CODE,
				requestedSuccessCode, "200, 204 and 303");
		}
	}
}
