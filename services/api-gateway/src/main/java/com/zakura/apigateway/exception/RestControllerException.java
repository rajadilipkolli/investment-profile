/* Licensed under Apache-2.0 2022 */
package com.zakura.apigateway.exception;

import org.springframework.http.HttpStatus;

public class RestControllerException extends RuntimeException {

    private static final long serialVersionUID = 5293541620167474447L;

    private final HttpStatus httpStatus;

    public RestControllerException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public RestControllerException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
