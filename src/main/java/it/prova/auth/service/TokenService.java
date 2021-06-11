package it.prova.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.auth.model.Token;
import it.prova.auth.repository.TokenRepository;

@Service
public class TokenService {
	@Autowired
	private TokenRepository tokenRepository;

	public void saveToken(Token token) {
		tokenRepository.save(token);
	}

	public Optional<Token> getToken(String token) {
		return tokenRepository.findByToken(token);
	}
}
