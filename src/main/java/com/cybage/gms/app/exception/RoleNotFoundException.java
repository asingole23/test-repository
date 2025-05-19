package com.cybage.gms.app.exception;

public class RoleNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7312305400918003985L;

	public RoleNotFoundException() {
		super("Error: Role is not found.");
	}

	public RoleNotFoundException(String message) {
		super(message);
	}

}
