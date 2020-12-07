package com.hector.test.apache.kafka.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.hector.test.apache.kafka.model.User;
import com.hector.test.apache.kafka.service.KafkaProducerService;

@Service("kafkaProducerService")
public class kafkaProducerServiceImpl implements KafkaProducerService{

	private static final Logger LOGGER = LoggerFactory.getLogger(kafkaProducerServiceImpl.class);

	@Autowired
	private KafkaTemplate<String, String> messageKafkaTemplate;

	@Autowired
	private KafkaTemplate<String, User> userKafkaTemplate;

	@Value("${message.topic.example:kafka_Example}")
	private String topicName;
	
	public void sendMessage(String topic,final String message) {
		if (topic==null || topic.trim().equals("")) {
			topic=topicName;
		}
		ListenableFuture<SendResult<String, String>> future = messageKafkaTemplate.send(topic, message);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			public void onSuccess(SendResult<String, String> result) {
				LOGGER.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			}
			public void onFailure(Throwable ex) {
				LOGGER.error("Unable to send message=[" + message + "] due to : " + ex.getMessage());
			}
		});
	}

	public void sendUser(String topic, User user) {
		if (topic==null || topic.trim().equals("")) {
			topic=topicName;
		}
		ListenableFuture<SendResult<String, User>> future = userKafkaTemplate.send(topic, user);
		future.addCallback(new ListenableFutureCallback<SendResult<String, User>>(){
			@Override
			public void onFailure(Throwable ex) {
				LOGGER.error("Unable to send message=[" + user + "] due to : " + ex.getMessage());
			}
			@Override
			public void onSuccess(SendResult<String, User> result) {
				LOGGER.info("Sent message=[" + user + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			}

		});

	}

}
