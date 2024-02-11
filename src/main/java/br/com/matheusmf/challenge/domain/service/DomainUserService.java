package br.com.matheusmf.challenge.domain.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import br.com.matheusmf.challenge.domain.User;
import br.com.matheusmf.challenge.domain.repository.UserRepository;

public class DomainUserService implements UserService {
	
	private final UserRepository userRepository;
	
	public DomainUserService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}

	@Override
	public Page<User> find(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
