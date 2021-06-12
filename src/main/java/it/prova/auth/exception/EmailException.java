package it.prova.auth.exception;

import org.springframework.http.HttpStatus;

public class EmailException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private HttpStatus status;

	public EmailException(String message) {
		super(message);
	}

	public EmailException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	public EmailException(HttpStatus status, Throwable cause) {
		super(cause);
		this.status = status;
	}

	public EmailException(String message, Throwable cause) {
		super(message, cause);
	}

	public String errorMessage() {
		return status.value() + ":".concat(getMessage());
	}

	public HttpStatus getStatus() {
		return status;
	}
}
