package br.com.matheusmf.challenge.infrastructure.in.kafka.consumer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.matheusmf.challenge.core.domain.User;
import br.com.matheusmf.challenge.core.port.out.UserPersistencePort;

@Service
public class NewUserConsumer {
	
	@Autowired
	private UserPersistencePort userPersistencePort;
	
	@KafkaListener(topics = "${spring.kafka.topics.new-user}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumeNewUser(String userId) {
		Optional<User> optionalUser = userPersistencePort.findById(userId);
		if (optionalUser.isPresent()) {
			System.out.println(String.format("A new user called %s was created with id %s!", 
					optionalUser.get().getName(), optionalUser.get().getId()));
		}
	}

}
