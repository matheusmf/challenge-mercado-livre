package br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.document.UserDocument;

@Repository
public interface SpringDataMongoUserRepository extends MongoRepository<UserDocument, String> {
	
	@Query("{ 'id': ?0 }")
	Optional<UserDocument> findById(String id);
	
	@Query(value = "{'name': {$regex : /?0/, $options: 'i'}}")
	Page<UserDocument> findByName(String name, Pageable pageable);
	
	@Query("{ 'cpf': ?0 }")
	Optional<UserDocument> findByCpf(String cpf);
	
	@Query("{'id':{$nin: [?1], $exists: true}, 'cpf' : ?0 }")
	Optional<UserDocument> findByCpfAndIdNot(String cpf, String id);
	
	@Query("{ 'email': ?0 }")
	Optional<UserDocument> findByEmail(String email);
	
	@Query("{'id':{$nin: [?1], $exists: true}, 'email' : ?0 }")
	Optional<UserDocument> findByEmailAndIdNot(String email, String id);
	
}
