package it.prova.auth.service;

import java.util.function.Predicate;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.constraints.Email;

import org.springframework.stereotype.Service;

@Service
public class EmailValidatorService implements Predicate<String> {
	@Override
	public boolean test(@Email String email) {
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
