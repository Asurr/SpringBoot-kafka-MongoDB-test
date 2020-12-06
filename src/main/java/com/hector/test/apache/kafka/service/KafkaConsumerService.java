package com.hector.test.apache.kafka.service;

import com.hector.test.apache.kafka.model.User;

public interface KafkaConsumerService {

	public void consume(String message); 	

	public void consumeJson(User user);

}
