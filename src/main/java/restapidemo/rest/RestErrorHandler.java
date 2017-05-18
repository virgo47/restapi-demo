package restapidemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import restapidemo.ThreadRenameFilter;

@ControllerAdvice
public class RestErrorHandler {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMissingParameter(MissingServletRequestParameterException e) {
		return createErrorResponse(ErrorResponse.MISSING_PARAMETER, e.toString());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMissingBody() {
		return createErrorResponse(ErrorResponse.MISSING_BODY, "Body is mandatory, but missing");
	}

	private ErrorResponse createErrorResponse(int code, String message) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.code = code;
		errorResponse.requestId = (String) RequestContextHolder.getRequestAttributes()
			.getAttribute(ThreadRenameFilter.REQ_ID_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		errorResponse.message = message;
		return errorResponse;
	}
}
