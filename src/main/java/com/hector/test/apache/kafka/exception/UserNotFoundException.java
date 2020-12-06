package com.hector.test.apache.kafka.exception;

import org.springframework.core.NestedRuntimeException;

public class UserNotFoundException extends NestedRuntimeException  {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message, Throwable cause) {
		super(String.format(message+" exception " + cause.toString()));
	}

	public UserNotFoundException(String message) {
		super(String.format(message));
	}

	public UserNotFoundException(Throwable cause) {
		super(String.format(cause.toString()));
	}

}
