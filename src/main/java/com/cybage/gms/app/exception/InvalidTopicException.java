package com.cybage.gms.app.exception;

public class InvalidTopicException extends RuntimeException {
	public InvalidTopicException() {
		super("The Topic does not exist");
	}
	public InvalidTopicException(String message) {
		super(message);
	}
}
