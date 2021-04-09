package com.zakura.apigateway.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ControllerAdvice("com.zakura.apigateway")
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String NO_RECORD_FOUND = "No Record Found";
	private static final String BAD_REQUEST = "Invalid request";
	private static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected internal server error";

	@ExceptionHandler({ RestControllerException.class })
	public final ResponseEntity<GatewayError> handleRestControllerException(RestControllerException e,
			WebRequest request) {
		if (e instanceof RecordNotFoundException) {
			return errorResponse(e);
		}

		if (e instanceof RecordNotFoundException) {
			return errorResponse(e);
		}
		log.warn("Unhandled RestControllerException");
		return handleUnexpectedException(e, request);
	}

	@ExceptionHandler({ RuntimeException.class })
	private ResponseEntity<GatewayError> handleUnexpectedException(RestControllerException e, WebRequest request) {
		log.error(UNEXPECTED_ERROR_MESSAGE, e);
		return errorResponse(new RestControllerException(UNEXPECTED_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR));
	}

	private ResponseEntity<GatewayError> errorResponse(RestControllerException e) {
		return errorResponse(e, new HttpHeaders());
	}

	private ResponseEntity<GatewayError> errorResponse(RestControllerException e, HttpHeaders headers) {
		return new ResponseEntity<>(new GatewayError(Integer.valueOf(e.getHttpStatus().value()), e.getMessage()), headers,
				e.getHttpStatus());
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException ex,
			WebRequest request) {
		ErrorResponse error = new ErrorResponse();
		error.setPath(request.getContextPath());
		error.setError(NO_RECORD_FOUND);
		error.setMessage(ex.getMessage());
		error.setException(ex.getClass().getSimpleName());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
}
