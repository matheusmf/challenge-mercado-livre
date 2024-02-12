package br.com.matheusmf.challenge.core.port.out;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.matheusmf.challenge.core.domain.User;

public interface UserPersistencePort {
	
	Optional<User> findById(String id);
	
	Page<User> findAll(Pageable pageable);
	
	Page<User> findByName(String name, Pageable pageable);
	
	Optional<User> findByCpf(String cpf);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByCpfAndIdNot(String cpf, String id);
	
	Optional<User> findByEmailAndIdNot(String email, String id);
	
	User save(User user);
	
	void delete(User user);

}
