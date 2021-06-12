package it.prova.auth.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.auth.exception.EmailException;
import it.prova.auth.exception.UserException;
import it.prova.auth.model.Token;
import it.prova.auth.registration.RegistrationRequest;
import it.prova.auth.service.RegistrationService;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/registration", produces = { MediaType.APPLICATION_JSON_VALUE })
public class RegistrationController {
	@Autowired
	private RegistrationService registrationService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Token register(@RequestBody RegistrationRequest request) {
		try {
			return registrationService.register(request);

		} catch (UserException e) {
			e.printStackTrace();
			throw new UserException(HttpStatus.CONFLICT, "User already taken");
		} catch (EmailException e) {
			e.printStackTrace();
			throw new EmailException(HttpStatus.BAD_REQUEST, "email not valid");
		}
	}

}
