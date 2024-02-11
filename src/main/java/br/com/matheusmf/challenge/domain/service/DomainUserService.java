package br.com.matheusmf.challenge.domain.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import br.com.matheusmf.challenge.domain.DomainException;
import br.com.matheusmf.challenge.domain.User;
import br.com.matheusmf.challenge.domain.repository.UserRepository;
import br.com.matheusmf.challenge.domain.service.validation.ValidationResult;

public class DomainUserService implements UserService {
	
	private final UserRepository userRepository;
	private final UserValidationService userValidationService;
	
	public DomainUserService(final UserRepository userRepository) {
		this.userRepository = userRepository;
		this.userValidationService = new DomainUserValidationService(userRepository);
	}

	@Override
	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}

	@Override
	public Page<User> find(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public User createUser(User user) {
		ValidationResult validation = userValidationService.validate(user);
		if (validation.notValid()) {
			throw new DomainException(validation.getErrorMsg());
		}
		user.assignCreateData();
		return userRepository.save(user);
	}
	
	@Override
	public User updateUser(User user) {
		ValidationResult validation = userValidationService.validate(user);
		if (validation.notValid()) {
			throw new DomainException(validation.getErrorMsg());
		}
		user.assignUpdateData();
		return userRepository.save(user);
	}

}
