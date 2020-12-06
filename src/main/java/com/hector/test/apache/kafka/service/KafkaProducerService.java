package com.hector.test.apache.kafka.service;

import com.hector.test.apache.kafka.model.User;


public interface KafkaProducerService {

	public void sendMessage(String topic,final String message);	

	public void sendUser(String topic, User user);

}
