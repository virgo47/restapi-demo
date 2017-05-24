package virgo47.restapidemo.rest;

/**
 * Static class with various check methods (like various Assert classes, but with its own
 * Exception class and working with our error codes.
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
}
