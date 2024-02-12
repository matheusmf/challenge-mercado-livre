package br.com.matheusmf.challenge.core.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.matheusmf.challenge.core.domain.User;

public interface UserServicePort {
	User findById(String id);
	
	Page<User> find(String name, Pageable pageable);
	
	User createUser(User user);
	
	User updateUser(String id, User user);
	
	void delete(String id);

}
