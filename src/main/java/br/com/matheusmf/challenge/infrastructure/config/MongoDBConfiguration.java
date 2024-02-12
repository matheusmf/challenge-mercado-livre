package br.com.matheusmf.challenge.infrastructure.config;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.repository.SpringDataMongoUserRepository;

@EnableMongoRepositories(basePackageClasses = SpringDataMongoUserRepository.class)
public class MongoDBConfiguration {

}
