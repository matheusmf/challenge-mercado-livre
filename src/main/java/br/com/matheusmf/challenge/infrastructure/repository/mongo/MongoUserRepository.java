package br.com.matheusmf.challenge.infrastructure.repository.mongo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Optional<User> findById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public Page<User> findByName(String name, Pageable pageable) {
		return userRepository.findByName(name, pageable);
	}
	
	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	@Override
	public Optional<User> findByCpf(String cpf) {
		return userRepository.findByCpf(cpf);
	}
	
	@Override
	public Optional<User> findByCpfAndIdNot(String cpf, String id) {
		return userRepository.findByCpfAndIdNot(cpf, id);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public Optional<User> findByEmailAndIdNot(String email, String id) {
		return userRepository.findByEmailAndIdNot(email, id);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

}
