package br.com.matheusmf.challenge.core.service;

import java.util.UUID;

import br.com.matheusmf.challenge.core.domain.Email;
import br.com.matheusmf.challenge.core.domain.User;
import br.com.matheusmf.challenge.core.port.in.EmailServicePort;
import br.com.matheusmf.challenge.core.port.out.EmailPersistencePort;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
@Named("emailService")
public class EmailService implements EmailServicePort {
	
	private final EmailPersistencePort emailPersistencePort;

	@Override
	public Email saveEmail(User user) {
		Email email = Email.builder()
						.id(UUID.randomUUID().toString())
						.to(user.getEmail())
						.welcomeMessage(String.format("Seja bem-vindo %s!", user.getName()))
						.build();
		return emailPersistencePort.saveEmail(email);
	}

}
