package com.example.sipservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Component
@ControllerAdvice("com.zakura.sipservice")
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private static final String NO_RECORD_FOUND = "No Record Found";
    private static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected internal server error";

    @ExceptionHandler({RestControllerException.class})
    public final ResponseEntity<StockError> handleRestControllerException(
            RestControllerException e, WebRequest request) {
        if (e instanceof RecordNotFoundException) {
            return errorResponse(e);
        }

        log.warn("Unhandled RestControllerException");
        return handleUnexpectedException(e, request);
    }

    @ExceptionHandler({RuntimeException.class})
    private ResponseEntity<StockError> handleUnexpectedException(
            RestControllerException e, WebRequest request) {
        log.error(UNEXPECTED_ERROR_MESSAGE, e);
        return errorResponse(
                new RestControllerException(
                        UNEXPECTED_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<StockError> errorResponse(RestControllerException e) {
        return errorResponse(e, new HttpHeaders());
    }

    private ResponseEntity<StockError> errorResponse(
            RestControllerException e, HttpHeaders headers) {
        return new ResponseEntity<>(
                new StockError(e.getHttpStatus().value(), e.getMessage()),
                headers,
                e.getHttpStatus());
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleRecordNotFoundException(
            RecordNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setPath(request.getContextPath());
        error.setError(NO_RECORD_FOUND);
        error.setMessage(ex.getMessage());
        error.setException(ex.getClass().getSimpleName());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
