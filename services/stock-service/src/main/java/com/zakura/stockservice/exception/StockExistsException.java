/* Licensed under Apache-2.0 2025 */
package com.zakura.stockservice.exception;

import java.io.Serial;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StockExistsException extends RestControllerException {

    @Serial private static final long serialVersionUID = 1L;

    private final String message;

    public StockExistsException(String name) {
        super("Stock with Name %s already exists".formatted(name), HttpStatus.CONFLICT);
        this.message = "Stock with Name %s already exists".formatted(name);
    }
}
