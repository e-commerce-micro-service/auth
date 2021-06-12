package it.prova.auth.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<JsonError> handleException(HttpServletRequest req, Exception e) {
		String errorURL = req.getRequestURL().toString();
		if (e instanceof UserException) {
			UserException pe = (UserException) e;
			return new ResponseEntity<>(new JsonError(errorURL, pe.getMessage(), pe.getStatus().toString()),
					pe.getStatus());
		}else if (e instanceof EmailException) {
			EmailException pe = (EmailException) e;
			return new ResponseEntity<>(new JsonError(errorURL, pe.getMessage(), pe.getStatus().toString()),
					pe.getStatus());
		}
		
		return new ResponseEntity<>(new JsonError(errorURL, "Unexpected Exception: " + e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
