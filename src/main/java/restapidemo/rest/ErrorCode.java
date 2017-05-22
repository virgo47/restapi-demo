package restapidemo.rest;

public enum ErrorCode {

	MISSING_BODY(10000),
	MISSING_PARAMETER(10001),

	// Body validation issues
	/** Mandatory attribute is missing. */
	MISSING_ATTRIBUTE(11001),

	/** Attribute is not allowed in this case (e.g. ID specified for creation). */
	FORBIDDEN_ATTRIBUTE(11002);

	public final int code;

	ErrorCode(int code) {
		this.code = code;
	}
}
