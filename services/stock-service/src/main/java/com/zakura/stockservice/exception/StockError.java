package com.zakura.stockservice.exception;

import lombok.Getter;

@Getter
public class StockError {
	private final Integer errorCode;
	private final String errorMessage;
	
	public StockError(Integer errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
