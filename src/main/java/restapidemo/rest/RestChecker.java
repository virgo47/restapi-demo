package restapidemo.rest;

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

	public static <T> T checkFound(T object) {
		if (object == null) {
			throw new NotFoundException();
		}
		return object;
	}
}
