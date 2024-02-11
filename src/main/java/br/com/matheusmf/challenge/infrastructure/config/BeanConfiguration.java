package br.com.matheusmf.challenge.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.matheusmf.challenge.ChallengeApplication;
import br.com.matheusmf.challenge.domain.repository.UserRepository;
import br.com.matheusmf.challenge.domain.service.DomainUserService;
import br.com.matheusmf.challenge.domain.service.UserService;

@Configuration
@ComponentScan(basePackageClasses = ChallengeApplication.class)
public class BeanConfiguration {
	
	@Bean
	UserService userService(final UserRepository userRepository) {
		return new DomainUserService(userRepository);
	}

}
