package it.prova.auth.security.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.prova.auth.mail.EmailRequest;
import it.prova.auth.model.Authority;
import it.prova.auth.model.AuthorityName;
import it.prova.auth.model.Token;
import it.prova.auth.model.User;
import it.prova.auth.security.jwt.dto.JwtUserDetailsImpl;
import it.prova.auth.security.repository.AuthorityRepository;
import it.prova.auth.security.repository.UserRepository;
import it.prova.auth.service.TokenService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return JwtUserDetailsImpl.build(user);
	}

	public Token signUpUser(User user) {
		boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();

		if (userExists) {
			throw new IllegalStateException("username already taken");
		}
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityRepository.findByName(AuthorityName.ROLE_USER).orElse(null));
		user.setAuthorities(authorities);

		userRepository.save(user);
		
		//TODO testare se funziona l'invio dell'email
//		sendEmail(user);
		
		String token = UUID.randomUUID().toString();

		Token confermationToken = new Token(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);

		tokenService.saveToken(confermationToken);

		return confermationToken;
	}

	public String sendEmail(User user) {
		String emailResourceUrl = "http://localhost/notify/api/sendmailconfirmregistration";
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<EmailRequest> request = new HttpEntity<>(
				new EmailRequest(user.getFirstName(), user.getLastName(), user.getEmail()));
		return restTemplate.postForObject(emailResourceUrl, request, String.class);

	}
}