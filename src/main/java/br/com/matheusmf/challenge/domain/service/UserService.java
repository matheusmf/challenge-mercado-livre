package br.com.matheusmf.challenge.domain.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.matheusmf.challenge.domain.User;

public interface UserService {
	Optional<User> findById(UUID id);
	
	Page<User> find(String name, Pageable pageable);
	
	User createUser(User user);
	
	User updateUser(User user);

}
