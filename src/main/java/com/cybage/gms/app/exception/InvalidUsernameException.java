package com.cybage.gms.app.exception;

public class InvalidUsernameException extends RuntimeException {
/**
	 * 
	 */
	private static final long serialVersionUID = 2531370359221061792L;

public InvalidUsernameException() {
	super("the provided username does not exist");
}

public InvalidUsernameException(String message) {
	super(message);
}
}
