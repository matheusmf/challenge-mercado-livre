package br.com.matheusmf.challenge.infrastructure.repository.mongo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.matheusmf.challenge.domain.User;
import br.com.matheusmf.challenge.domain.repository.UserRepository;

@Component
public class MongoUserRepository implements UserRepository {
	
	private final SpringDataMongoUserRepository userRepository;
	
	@Autowired
	public MongoUserRepository(final SpringDataMongoUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}

	@Override
	public Page<User> findByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

}
