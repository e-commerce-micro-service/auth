package it.prova.auth.exception;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private HttpStatus status;
	
	public UserException(String message) {
		super(message);
	}

	public UserException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	public UserException(HttpStatus status, Throwable cause) {
		super(cause);
		this.status = status;
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}

	public String errorMessage() {
		return status.value() + ":".concat(getMessage());
	}

	public HttpStatus getStatus() {
		return status;
	}

}
