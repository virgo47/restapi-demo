package virgo47.restapidemo.rest;

public enum ErrorCode {

	NOT_FOUND(404),
	MISSING_BODY(10000),
	MISSING_PARAMETER(10001),
	INVALID_PARAMETER_VALUE(10002),

	// Body validation issues
	/** Mandatory attribute is missing. */
	MISSING_ATTRIBUTE(11001),

	/** Attribute is not allowed in this case (e.g. ID specified for creation). */
	FORBIDDEN_ATTRIBUTE(11002),

	/** ID from path does not match the ID from body. */
	ID_MISMATCH(11010),

	/** Current state of the resource does not permit the operation. */
	ILLEGAL_STATE(12000);

	public final int codeValue;

	ErrorCode(int codeValue) {
		this.codeValue = codeValue;
	}
}
