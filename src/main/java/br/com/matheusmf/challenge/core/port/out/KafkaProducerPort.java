package br.com.matheusmf.challenge.core.port.out;

public interface KafkaProducerPort {
	
	void sendToNewUserTopic(String userId);

}
