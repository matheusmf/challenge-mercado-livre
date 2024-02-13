package br.com.matheusmf.challenge.infrastructure.in.kafka.consumer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.matheusmf.challenge.core.domain.User;
import br.com.matheusmf.challenge.core.port.in.EmailServicePort;
import br.com.matheusmf.challenge.core.port.out.UserPersistencePort;

@Service
public class NewUserConsumer {
	
	@Autowired
	private UserPersistencePort userPersistencePort;
	
	@Autowired
	private EmailServicePort emailServicePort;
	
	@KafkaListener(topics = "${spring.kafka.topics.new-user}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumeNewUser(String userId) {
		Optional<User> optionalUser = userPersistencePort.findById(userId);
		if (optionalUser.isPresent()) {
			emailServicePort.saveEmail(optionalUser.get());
		}
	}

}
