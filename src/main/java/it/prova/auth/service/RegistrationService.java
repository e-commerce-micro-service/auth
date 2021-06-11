package it.prova.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.auth.model.Token;
import it.prova.auth.model.User;
import it.prova.auth.registration.RegistrationRequest;
import it.prova.auth.security.service.JwtUserDetailsServiceImpl;
@Service
public class RegistrationService {
	@Autowired
	private EmailValidatorService emailValidator;
	@Autowired
	private JwtUserDetailsServiceImpl userService;

	public Token register(RegistrationRequest request) {
		boolean isValidEmail = emailValidator.test(request.getEmail());
		if (!isValidEmail) {
			throw new IllegalStateException("email not valid");
		}
		
		
		Token token = userService.signUpUser(new User(request.getFirstName(), request.getLastName(),
				request.getUsername(), request.getPassword(), request.getEmail()));

		
		return token;

	}

}
