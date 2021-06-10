package it.prova.auth;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.prova.auth.model.Authority;
import it.prova.auth.model.AuthorityName;
import it.prova.auth.model.User;
import it.prova.auth.security.repository.AuthorityRepository;
import it.prova.auth.security.repository.UserRepository;

@SpringBootApplication
public class AuthApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorityRepository authorityRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		Authority authorityAdmin = authorityRepository.findByName(AuthorityName.ROLE_ADMIN).orElse(null);
		if (authorityAdmin == null) {
			authorityAdmin = new Authority(AuthorityName.ROLE_ADMIN);
			authorityAdmin = authorityRepository.save(authorityAdmin);
		}

		Authority authorityPrincipal = authorityRepository.findByName(AuthorityName.ROLE_SHOPKEEPER).orElse(null);
		if (authorityPrincipal == null) {
			authorityPrincipal = new Authority(AuthorityName.ROLE_SHOPKEEPER);
			authorityRepository.save(authorityPrincipal);
		}

		Authority authorityUser = authorityRepository.findByName(AuthorityName.ROLE_USER).orElse(null);
		if (authorityUser == null) {
			authorityUser = new Authority(AuthorityName.ROLE_USER);
			authorityUser = authorityRepository.save(authorityUser);
		}

		User user = userRepository.findByUsername("admin").orElse(null);

		if (user == null) {

			List<Authority> authorities = Arrays.asList(new Authority[] { authorityAdmin, authorityUser });

			user = new User();
			user.setAuthorities(authorities);
			user.setEnabled(true);
			user.setUsername("admin");
			user.setPassword("asd");
			user.setPassword(passwordEncoder.encode("asd"));
			user.setEmail("admin@example.com");

			user = userRepository.save(user);

		}

		User commonUser = userRepository.findByUsername("commonUser").orElse(null);

		if (commonUser == null) {

			List<Authority> authorities = Arrays.asList(new Authority[] { authorityUser });

			commonUser = new User();
			commonUser.setAuthorities(authorities);
			commonUser.setEnabled(true);
			commonUser.setUsername("commonUser");
			commonUser.setPassword(passwordEncoder.encode("asd"));
			commonUser.setEmail("commonUser@example.com");

			commonUser = userRepository.save(commonUser);

		}
	}
}
