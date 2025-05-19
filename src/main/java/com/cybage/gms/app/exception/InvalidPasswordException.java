package com.cybage.gms.app.exception;

public class InvalidPasswordException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6810148694416649611L;

	public InvalidPasswordException() {
		super("The provided password is wrong");
	}

	public InvalidPasswordException(String message) {
		super(message);
	}

}
