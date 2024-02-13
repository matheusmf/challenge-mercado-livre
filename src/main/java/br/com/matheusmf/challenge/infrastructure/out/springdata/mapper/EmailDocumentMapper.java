package br.com.matheusmf.challenge.infrastructure.out.springdata.mapper;

import br.com.matheusmf.challenge.core.domain.Email;
import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.document.EmailDocument;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named("emailDocumentMapper")
public class EmailDocumentMapper {
	
	public EmailDocument toDocument(Email email) {
		return EmailDocument.builder()
				.id(email.getId())
				.to(email.getTo())
				.welcomeMessage(email.getWelcomeMessage())
				.build();
	}
	
	public Email toDomain(EmailDocument emailDocument) {
		return Email.builder()
				.id(emailDocument.getId())
				.to(emailDocument.getTo())
				.welcomeMessage(emailDocument.getWelcomeMessage())
				.build();
	}

}
