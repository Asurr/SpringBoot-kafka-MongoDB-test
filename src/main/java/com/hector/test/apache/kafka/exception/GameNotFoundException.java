package com.hector.test.apache.kafka.exception;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GameNotFoundException extends NestedRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public GameNotFoundException(String message, Throwable cause) {
		super(String.format(message+" exception " + cause.toString()));
	}

	public GameNotFoundException(String message) {
		super(String.format(message));
	}

	public GameNotFoundException(Throwable cause) {
		super(String.format(cause.toString()));
	}

}
