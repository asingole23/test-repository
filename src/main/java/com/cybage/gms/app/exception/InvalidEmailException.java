package com.cybage.gms.app.exception;

public class InvalidEmailException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8159666290771544714L;

	public InvalidEmailException() {
		super("The provided email is not registered");
	}

	public InvalidEmailException(String message) {
		super(message);
	}

}
