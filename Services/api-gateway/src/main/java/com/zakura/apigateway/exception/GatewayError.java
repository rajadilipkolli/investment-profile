package com.zakura.apigateway.exception;

public class GatewayError {
	private final Integer errorCode;
	private final String errorMessage;
	
	public GatewayError(Integer errorCode, String errorMessage) {
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
