package virgo47.restapidemo.rest;

import virgo47.restapidemo.ThreadRenameFilter;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@ControllerAdvice
public class RestErrorHandler {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMissingParameter(MissingServletRequestParameterException e) {
		return createErrorResponse(ErrorCode.MISSING_PARAMETER, e.toString());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMissingBody() {
		return createErrorResponse(ErrorCode.MISSING_BODY, "Body is mandatory, but missing");
	}

	@ExceptionHandler(CheckException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handlePreconditionFailure(CheckException e) {
		return createErrorResponse(e.code, e.getMessage());
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNotFound() {
		return createErrorResponse(ErrorCode.NOT_FOUND, "Resource not found");
	}

	private ErrorResponse createErrorResponse(ErrorCode code, String message) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.code = code.codeValue;
		errorResponse.requestId = (String) RequestContextHolder.getRequestAttributes()
			.getAttribute(ThreadRenameFilter.REQ_ID_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		errorResponse.message = message;
		return errorResponse;
	}
}
