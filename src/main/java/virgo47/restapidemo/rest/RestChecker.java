package virgo47.restapidemo.rest;

import virgo47.restapidemo.entities.Entity;

/**
 * Static class with various check methods (like various Assert classes, but with its own
 * Exception class and working with our error codes.
 *
 * @noinspection WeakerAccess, SameParameterValue
 */
public class RestChecker {

	public static void checkForbiddenAttribute(Object attributeValue, String errorMessage) {
		if (attributeValue != null) {
			throw new CheckException(ErrorCode.FORBIDDEN_ATTRIBUTE, errorMessage);
		}
	}

	public static void checkState(boolean condition, String errorMessage) {
		if (!condition) {
			throw new CheckException(ErrorCode.ILLEGAL_STATE, errorMessage);
		}
	}

	/** Enforces 404 Not Found if object is null. */
	public static <T> T checkFound(T object) {
		if (object == null) {
			throw new NotFoundException();
		}
		return object;
	}

	public static CheckException invalidParameterValueException(
		String parameterName, Object value, String validValuesString)
	{
		return new CheckException(ErrorCode.INVALID_PARAMETER_VALUE,
			"Invalid value " + value + " used for parameter " + parameterName
				+ ", valid values are: " + validValuesString);
	}

	public static void checkUpdatedIdMatch(Long id, Entity entity) {
		if (!id.equals(entity.getId())) {
			throw new CheckException(ErrorCode.ID_MISMATCH,
				"ID from path (" + id + ") does not match ID from body (" + entity.getId() + ").");
		}
	}
}
