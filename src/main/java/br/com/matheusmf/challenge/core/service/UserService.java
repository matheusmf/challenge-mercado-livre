package br.com.matheusmf.challenge.core.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.matheusmf.challenge.core.domain.DomainException;
import br.com.matheusmf.challenge.core.domain.User;
import br.com.matheusmf.challenge.core.port.in.UserServicePort;
import br.com.matheusmf.challenge.core.port.out.UserPersistencePort;
import br.com.matheusmf.challenge.core.service.validation.ValidationResult;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
@Named("userService")
public class UserService implements UserServicePort {

	private final UserPersistencePort userPersistencePort;
	private final UserValidationService userValidationService;

	@Override
	public User findById(String id) {
		return userPersistencePort.findById(id).orElseThrow(() -> new DomainException("No records found for this ID!"));
	}

	@Override
	public Page<User> find(String name, Pageable pageable) {
		if (StringUtils.hasText(name)) {
			return userPersistencePort.findByName(name, pageable);
		}
		return userPersistencePort.findAll(pageable);
	}

	@Override
	public User createUser(User user) {
		ValidationResult validation = userValidationService.validate(user);
		if (validation.notValid()) {
			throw new DomainException(validation.getErrorMsg());
		}
		user.setId(UUID.randomUUID().toString());
		user.setCreatedAt(LocalDateTime.now());
		return userPersistencePort.save(user);
	}

	@Override
	public User updateUser(String id, User user) {
		User existingUser = userPersistencePort.findById(id)
				.orElseThrow(() -> new DomainException("No records found for this ID!"));
		existingUser.setName(user.getName());
		existingUser.setCpf(user.getCpf());
		existingUser.setEmail(user.getEmail());
		existingUser.setBirthdate(user.getBirthdate());

		ValidationResult validation = userValidationService.validate(existingUser);
		if (validation.notValid()) {
			throw new DomainException(validation.getErrorMsg());
		}

		existingUser.setLastUpdatedAt(LocalDateTime.now());
		return userPersistencePort.save(existingUser);
	}

	@Override
	public void delete(String id) {
		User existingUser = userPersistencePort.findById(id)
				.orElseThrow(() -> new DomainException("No records found for this ID!"));
		userPersistencePort.delete(existingUser);
	}

}
