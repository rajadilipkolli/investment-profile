/* Licensed under Apache-2.0 2025 */
package com.zakura.stockservice.exception;

import java.io.Serial;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestControllerException extends RuntimeException {

    @Serial private static final long serialVersionUID = 5293541620167474447L;

    private final HttpStatus httpStatus;
    private final String message;

    public RestControllerException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
