package com.hector.test.apache.kafka.service;

import java.util.List;

import com.hector.test.apache.kafka.model.User;

public interface KafkaConsumerService {

	public void consume(String message); 	

	public void consumeJson(User user);
	
	public List<String> consumeTopicDays(String topic,long days);
	
	public List<User> consumeJsonTopicDays(String topic,long days);

}
