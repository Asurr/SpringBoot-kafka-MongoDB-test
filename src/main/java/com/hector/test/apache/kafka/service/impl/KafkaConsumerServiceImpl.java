package com.hector.test.apache.kafka.service.impl;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import com.hector.test.apache.kafka.config.KafkaConfiguration;
import com.hector.test.apache.kafka.model.User;
import com.hector.test.apache.kafka.service.KafkaConsumerService;

@Service("KafkaConsumerService")
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private KafkaConfiguration kafkaConfiguration;
	
    private final CountDownLatch latch1 = new CountDownLatch(1);
    
	//Aqui si falla logeamos el mensaje y seguimos con el siguiente
	@KafkaListener(topics = "${message.topic.example:kafka_Example}", groupId = "${message.group.name:group_id}",containerFactory="messagesKafkaListenerContainerFactory",errorHandler = "listen3ErrorHandler")
	public void consume(String message) {
		if (message.equals("error")) {
			throw new RuntimeException("failed");
		}
		LOGGER.info("Recieved Message of kafka_example in  listener: " + message);
	}

	@KafkaListener(topics = "${message.topic.example.json:kafka_Example_json}", groupId = "${group_json",containerFactory="userKafkaListenerContainerFactory")
	public void consumeJson(User user) {
		userServiceImpl.saveUser(user);
		LOGGER.info("Recieved Message of kafka_example_json in  listener: " + user);
	}

	@KafkaListener(topics = "${message.topic.example:kafka_Example}", groupId = "${message.group.name:group_id}",containerFactory="messagesKafkaListenerContainerFactory",errorHandler = "listen3ErrorHandler")
	public List<String> consumeTopicDays(String topic, long days) {

		List<String> messages = Collections.emptyList();		
		ConsumerFactory<String, String> consumerFactory = kafkaConfiguration.messageConsumerFactory();

		Consumer<String, String> consumer = consumerFactory.createConsumer();

		consumer.subscribe(Collections.singletonList(topic));

		// mensajes de un topic por dias
		ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofDays(days));
		consumerRecords.forEach(action -> {
			LOGGER.info("Message : " + action.value());
			messages.add((String) action.value());
		});
		return messages;
	}


	@KafkaListener(topics = "${message.topic.example.json:kafka_Example_json}", groupId = "${group_json",containerFactory="userKafkaListenerContainerFactory")
	public List<User> consumeJsonTopicDays(String topic, long days) {

		List<User> users = Collections.emptyList();		

//		ConsumerFactory<String, User> consumerFactory = getConsumerFactoryInstance();
//
//		Consumer<String, User> consumer = consumerFactory.createConsumer();
//
//		consumer.subscribe(Collections.singletonList(topic));
//
//		// users de un topic por dias
//		ConsumerRecords<String, User> consumerRecords = consumer.poll(Duration.ofDays(days));
//
//		consumerRecords.forEach(action -> {
//			LOGGER.info("User : " + action.value());
//			users.add((User) action.value());
//		});

		return users;
	}
	

}
