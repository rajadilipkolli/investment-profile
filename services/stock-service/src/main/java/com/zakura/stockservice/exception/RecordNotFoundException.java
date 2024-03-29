package com.zakura.stockservice.exception;

import java.io.Serial;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class RecordNotFoundException extends RestControllerException {
	
	@Serial
	private static final long serialVersionUID = 7270193384232612561L;

	public RecordNotFoundException(String message) {
		super("%s not found.".formatted(message), NOT_FOUND);
	}

}
