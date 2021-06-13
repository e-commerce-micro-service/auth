package it.prova.auth.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.prova.auth.dto.UserDetailsImpl;
import it.prova.auth.exception.UserException;
import it.prova.auth.mail.EmailRequest;
import it.prova.auth.model.Authority;
import it.prova.auth.model.AuthorityName;
import it.prova.auth.model.Token;
import it.prova.auth.model.User;
import it.prova.auth.repository.AuthorityRepository;
import it.prova.auth.repository.UserRepository;
import it.prova.auth.security.jwt.JwtTokenUtil;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

	public Token signUpUser(User user) {
		boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();

		if (userExists) {
			throw new UserException("username already taken");
		}

		registerUser(user);
//		sendEmail(user);

		Token confermationToken = new Token(jwtTokenUtil.generateJwtToken(user), LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15), user);
		tokenService.saveToken(confermationToken);

		return confermationToken;
	}

	public void registerUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityRepository.findByName(AuthorityName.ROLE_USER).orElse(null));
		user.setAuthorities(authorities);

		userRepository.save(user);

	}

	public String sendEmail(User user) {
		String emailResourceUrl = "http://localhost:8081/api/sendmailconfirmregistration";
		HttpEntity<EmailRequest> request = new HttpEntity<>(
				new EmailRequest(user.getFirstName(), user.getLastName(), user.getEmail()));
		return restTemplate.postForObject(emailResourceUrl, request, String.class);

	}
}