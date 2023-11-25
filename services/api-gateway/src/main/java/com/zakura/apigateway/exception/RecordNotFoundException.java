/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class RecordNotFoundException extends RestControllerException {

    private static final long serialVersionUID = 7270193384232612561L;

    public RecordNotFoundException(String message) {
        super("%s not found.".formatted(message), NOT_FOUND);
    }
}
