package com.cybage.gms.app.exception;

public class InvalidIdException extends RuntimeException {
	public InvalidIdException() {
		super("The id provided does not exist");
	}
	public InvalidIdException(String message) {
		super(message);
	}
}
