package br.com.matheusmf.challenge.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.matheusmf.challenge.domain.User;

public interface UserRepository {
	
	Optional<User> findById(String id);
	
	Page<User> findByName(String name, Pageable pageable);
	
	Page<User> findAll(Pageable pageable);
	
	Optional<User> findByCpf(String cpf);
	
	Optional<User> findByCpfAndIdNot(String cpf, String id);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByEmailAndIdNot(String email, String id);
	
	User save(User user);

	void delete(User user);

}
