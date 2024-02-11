package br.com.matheusmf.challenge.infrastructure.repository.mongo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.matheusmf.challenge.domain.User;

@Repository
public interface SpringDataMongoUserRepository extends MongoRepository<User, String> {
	
	@Query("{ 'id': ?0 }")
	Optional<User> findById(String id);
	
	@Query(value = "{'name': {$regex : /?0/, $options: 'i'}}")
	Page<User> findByName(String name, Pageable pageable);
	
	@Query("{ 'cpf': ?0 }")
	Optional<User> findByCpf(String cpf);
	
	@Query("{'id':{$nin: [?1], $exists: true}, 'cpf' : ?0 }")
	Optional<User> findByCpfAndIdNot(String cpf, String id);
	
	@Query("{ 'email': ?0 }")
	Optional<User> findByEmail(String email);
	
	@Query("{'id':{$nin: [?1], $exists: true}, 'email' : ?0 }")
	Optional<User> findByEmailAndIdNot(String email, String id);
	
}
