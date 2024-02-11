package br.com.matheusmf.challenge.infrastructure.config;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import br.com.matheusmf.challenge.infrastructure.repository.mongo.SpringDataMongoUserRepository;

@EnableMongoRepositories(basePackageClasses = SpringDataMongoUserRepository.class)
public class MongoDBConfiguration {

}
