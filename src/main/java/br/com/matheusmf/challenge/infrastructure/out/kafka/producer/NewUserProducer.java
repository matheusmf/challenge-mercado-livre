package br.com.matheusmf.challenge.infrastructure.out.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NewUserProducer {
	
	@Value(value = "${spring.kafka.topics.new-user}")
    private String newUserTopic;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendToNewUserTopic(String userId) {
		kafkaTemplate.send(newUserTopic, userId);
	}

}
