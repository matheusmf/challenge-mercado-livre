package br.com.matheusmf.challenge.infrastructure.out.kafka.adapter;

import br.com.matheusmf.challenge.core.port.out.KafkaProducerPort;
import br.com.matheusmf.challenge.infrastructure.out.kafka.producer.NewUserProducer;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
@Named("kafkaProducerAdapter")
public class KafkaProducerAdapter implements KafkaProducerPort {
	
	private final NewUserProducer producer;

	@Override
	public void sendToNewUserTopic(String userId) {
		producer.sendToNewUserTopic(userId);
	}

}
