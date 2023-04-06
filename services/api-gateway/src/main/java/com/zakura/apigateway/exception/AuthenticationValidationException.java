/* Licensed under Apache-2.0 2022 */
package com.zakura.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.LocalDateTime;

public class AuthenticationValidationException extends ErrorResponseException {
    public AuthenticationValidationException(String message) {
        super(HttpStatus.BAD_REQUEST, asProblemDetail(message), null);
    }

    private static ProblemDetail asProblemDetail(String message) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
        problemDetail.setTitle("Validation Failed");
        problemDetail.setType(URI.create("https://api.microservices.com/errors/bad-request"));
        problemDetail.setProperty("errorCategory", "Validation");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
}
