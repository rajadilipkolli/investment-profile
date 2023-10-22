package com.zakura.stockservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class RestControllerException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 5293541620167474447L;

	private final HttpStatus httpStatus;
	private final String message;
	
	public RestControllerException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
		this.message = message;
	}

}
