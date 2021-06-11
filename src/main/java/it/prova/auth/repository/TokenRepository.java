package it.prova.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.prova.auth.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
	Optional<Token> findByToken(String token);
}
