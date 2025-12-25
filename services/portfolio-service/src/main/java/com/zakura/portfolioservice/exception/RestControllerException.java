/* Licensed under Apache-2.0 2025 */
package com.zakura.portfolioservice.exception;

import java.io.Serial;
import org.springframework.http.HttpStatus;

public class RestControllerException extends RuntimeException {

    @Serial private static final long serialVersionUID = 5293541620167474447L;

    private final HttpStatus httpStatus;

    public RestControllerException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
