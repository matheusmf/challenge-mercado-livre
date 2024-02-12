package br.com.matheusmf.challenge.infrastructure.out.springdata.adapter;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.matheusmf.challenge.core.domain.User;
import br.com.matheusmf.challenge.core.port.out.UserPersistencePort;
import br.com.matheusmf.challenge.infrastructure.out.springdata.mapper.UserDocumentMapper;
import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.document.UserDocument;
import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.repository.SpringDataMongoUserRepository;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
@Named("userSpringDataAdapter")
public class UserSpringDataAdapter implements UserPersistencePort {
	
	private final SpringDataMongoUserRepository userRepository;
	private final UserDocumentMapper mapper;
	
	@Override
	public Optional<User> findById(String id) {
		return userRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public Page<User> findByName(String name, Pageable pageable) {
		return userRepository.findByName(name, pageable).map(mapper::toDomain);
	}
	
	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable).map(mapper::toDomain);
	}
	
	@Override
	public Optional<User> findByCpf(String cpf) {
		return userRepository.findByCpf(cpf).map(mapper::toDomain);
	}
	
	@Override
	public Optional<User> findByCpfAndIdNot(String cpf, String id) {
		return userRepository.findByCpfAndIdNot(cpf, id).map(mapper::toDomain);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email).map(mapper::toDomain);
	}
	
	@Override
	public Optional<User> findByEmailAndIdNot(String email, String id) {
		return userRepository.findByEmailAndIdNot(email, id).map(mapper::toDomain);
	}

	@Override
	public User save(User user) {
		UserDocument document = mapper.toDocument(user);
		document = userRepository.save(document);
		return mapper.toDomain(document);
	}
	
	@Override
	public void delete(User user) {
		userRepository.delete(mapper.toDocument(user));
	}

}
