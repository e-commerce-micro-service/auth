package it.prova.auth.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.auth.model.Token;
import it.prova.auth.registration.RegistrationRequest;
import it.prova.auth.service.RegistrationService;

@RestController
@RequestMapping(value = "/api/registration", produces = { MediaType.APPLICATION_JSON_VALUE })
public class RegistrationController {
	@Autowired
	private RegistrationService registrationService;

	@PostMapping
	public Token register(@RequestBody RegistrationRequest request) {
		return registrationService.register(request);
	}

}
