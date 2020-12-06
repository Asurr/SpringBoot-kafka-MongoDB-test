package com.hector.test.apache.kafka.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.hector.test.apache.kafka.model.User;
import com.hector.test.apache.kafka.service.KafkaConsumerService;

@Service("KafkaConsumerService")
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

	//Aqui si falla logeamos el mensaje y seguimos con el siguiente
	@KafkaListener(topics = "${message.topic.example:kafka_Example}", groupId = "${message.group.name:group_id}",containerFactory="messagesKafkaListenerContainerFactory")
	public void consume(String message) {		
		if (message.startsWith("foo")) {
			throw new RuntimeException("failed");
		}
		LOGGER.info("Recieved Message of kafka_example in  listener: " + message);
	}

	@KafkaListener(topics = "${message.topic.example.json:kafka_Example_json}", groupId = "${group_json",containerFactory="userKafkaListenerContainerFactory")
	public void consumeJson(User user) {
		LOGGER.info("Recieved Message of kafka_example_json in  listener: " + user);
	}

}
