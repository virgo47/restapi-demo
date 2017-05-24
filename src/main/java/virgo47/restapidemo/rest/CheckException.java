package virgo47.restapidemo.rest;

public class CheckException extends RuntimeException {

	public final ErrorCode code;

	public CheckException(ErrorCode code, String message) {
		super(message);
		this.code = code;
	}
}
