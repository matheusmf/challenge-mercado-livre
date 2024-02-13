package br.com.matheusmf.challenge.infrastructure.out.springdata.adapter;

import br.com.matheusmf.challenge.core.domain.Email;
import br.com.matheusmf.challenge.core.port.out.EmailPersistencePort;
import br.com.matheusmf.challenge.infrastructure.out.springdata.mapper.EmailDocumentMapper;
import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.document.EmailDocument;
import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.repository.SpringDataMongoEmailRepository;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
@Named("emailSpringDataAdapter")
public class EmailSpringDataAdapter implements EmailPersistencePort {
	
	private final SpringDataMongoEmailRepository repository;
	private final EmailDocumentMapper mapper;
	
	@Override
	public Email saveEmail(Email email) {
		EmailDocument document = mapper.toDocument(email);
		document = repository.save(document);
		return mapper.toDomain(document);
	}

}
