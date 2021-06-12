package it.prova.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		System.out.println("REST TEMPLATE CONFIGURATO CORRETTAMENTE.");
		return new RestTemplate();
	}

}
