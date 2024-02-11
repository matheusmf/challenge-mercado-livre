package br.com.matheusmf.challenge.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

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
	public User findById(String id) {
		return userRepository.findById(id).orElseThrow(() -> new DomainException("No records found for this ID!"));
	}

	@Override
	public Page<User> find(String name, Pageable pageable) {
		if (StringUtils.hasText(name)) {
			return userRepository.findByName(name, pageable);
		}
		return userRepository.findAll(pageable);
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
	public User updateUser(String id, User user) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new DomainException("No records found for this ID!"));
		existingUser.setName(user.getName());
		existingUser.setCpf(user.getCpf());
		existingUser.setEmail(user.getEmail());
		existingUser.setBirthdate(user.getBirthdate());

		ValidationResult validation = userValidationService.validate(existingUser);
		if (validation.notValid()) {
			throw new DomainException(validation.getErrorMsg());
		}

		existingUser.assignUpdateData();
		return userRepository.save(existingUser);
	}

	@Override
	public void delete(String id) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new DomainException("No records found for this ID!"));
		userRepository.delete(existingUser);
	}

}
