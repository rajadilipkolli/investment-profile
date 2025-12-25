/* Licensed under Apache-2.0 2025 */
package com.zakura.portfolioservice.exception;

public class StockError {
    private final Integer errorCode;
    private final String errorMessage;

    public StockError(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
