/* Licensed under Apache-2.0 2025 */
package com.zakura.stockservice.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.io.Serial;

public class RecordNotFoundException extends RestControllerException {

    @Serial private static final long serialVersionUID = 7270193384232612561L;

    public RecordNotFoundException(String message) {
        super("%s not found.".formatted(message), NOT_FOUND);
    }
}
