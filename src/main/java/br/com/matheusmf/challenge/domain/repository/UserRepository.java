package br.com.matheusmf.challenge.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import br.com.matheusmf.challenge.domain.User;

public interface UserRepository {
	
	Optional<User> findById(UUID id);
	
	Page<User> findByName(String name);
	
	boolean existsByCpf(String cpf);
	
	boolean existsByEmail(String email);
	
	User save(User user);

}
