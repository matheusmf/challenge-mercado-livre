package br.com.matheusmf.challenge.infrastructure.repository.mongo;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.matheusmf.challenge.domain.User;

@Repository
public interface SpringDataMongoUserRepository extends MongoRepository<User, UUID> {
	
	Page<User> findByName(String name);
	
	boolean existsByCpf(String cpf);
	
	boolean existsByEmail(String email);

}
