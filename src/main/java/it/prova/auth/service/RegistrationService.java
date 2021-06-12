package it.prova.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.auth.exception.EmailException;
import it.prova.auth.model.Token;
import it.prova.auth.model.User;
import it.prova.auth.registration.RegistrationRequest;

@Service
public class RegistrationService {
	@Autowired
	private EmailValidatorService emailValidator;
	@Autowired
	private UserService userService;

	public Token register(RegistrationRequest request) {
		boolean isValidEmail = emailValidator.test(request.getEmail());
		if (!isValidEmail) {
			throw new EmailException("email not valid");
		}

		Token token = userService.signUpUser(new User(request.getFirstName(), request.getLastName(),
				request.getUsername(), request.getPassword(), request.getEmail()));

		return token;

	}

}
