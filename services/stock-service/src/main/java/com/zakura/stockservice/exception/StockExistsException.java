/* Licensed under Apache-2.0 2025 */
package com.zakura.stockservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StockExistsException extends RestControllerException {

    private final String message;

    public StockExistsException(String name) {
        super(name.formatted("Stock with Name %s already exists"), HttpStatus.CONFLICT);
        this.message = name.formatted("Stock with Name %s already exists");
    }
}
