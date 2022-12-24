/* Licensed under Apache-2.0 2022 */
package com.zakura.apigateway.exception;

import org.springframework.http.HttpStatus;

public class UserAuthenticationFailedException extends RestControllerException {

    private static final long serialVersionUID = -186875025084251028L;

    public UserAuthenticationFailedException(HttpStatus httpStatus) {
        super(httpStatus);
    }
}
