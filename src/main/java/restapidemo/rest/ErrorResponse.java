package restapidemo.rest;

public class ErrorResponse {

	public static final int MISSING_BODY = 10000;
	public static final int MISSING_PARAMETER = 10001;

	// Body validation issues
	public static final int MISSING_ATTRIBUTE = 11001;

	public int code;
	public String requestId;
	public String message;
}
