package br.com.matheusmf.challenge.infrastructure.out.springdata.mapper;

import br.com.matheusmf.challenge.core.domain.User;
import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.document.UserDocument;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named("userDocumentMapper")
public class UserDocumentMapper {
	
	public UserDocument toDocument(User user) {
		return UserDocument.builder()
				.id(user.getId())
				.name(user.getName())
				.cpf(user.getCpf())
				.email(user.getEmail())
				.birthdate(user.getBirthdate())
				.createdAt(user.getCreatedAt())
				.lastUpdatedAt(user.getLastUpdatedAt())
				.build();
	}
	
	public User toDomain(UserDocument userDocument) {
		return User.builder()
				.id(userDocument.getId())
				.name(userDocument.getName())
				.cpf(userDocument.getCpf())
				.email(userDocument.getEmail())
				.birthdate(userDocument.getBirthdate())
				.createdAt(userDocument.getCreatedAt())
				.lastUpdatedAt(userDocument.getLastUpdatedAt())
				.build();
	}

}
