package it.prova.auth.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.prova.auth.model.Authority;
import it.prova.auth.model.AuthorityName;
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Optional<Authority> findByName(AuthorityName name);

}