package br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.document.EmailDocument;

@Repository
public interface SpringDataMongoEmailRepository extends MongoRepository<EmailDocument, String> {

}
