package br.com.matheusmf.challenge.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.matheusmf.challenge.domain.User;

public interface UserService {
	User findById(String id);
	
	Page<User> find(String name, Pageable pageable);
	
	User createUser(User user);
	
	User updateUser(String id, User user);

}
