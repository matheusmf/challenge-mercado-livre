package br.com.matheusmf.challenge.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.matheusmf.challenge.domain.User;

public interface UserRepository {
	
	Optional<User> findById(UUID id);
	
	Page<User> findByName(String name, Pageable pageable);
	
	Page<User> findAll(Pageable pageable);
	
	boolean existsByCpf(String cpf);
	
	boolean existsByEmail(String email);
	
	User save(User user);

}
