package com.hector.test.apache.kafka.config;


import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.hector.test.apache.kafka.model.User;

@EnableKafka
@Configuration
public class KafkaConfiguration {


	//---------------------------------
	//	
	//		CONSUMER TEMPLATES
	//
	//---------------------------------

	//messages template consumer config
	@Bean
	public ConsumerFactory<String, String> messageConsumerFactory() {
		Map<String, Object> config = new HashMap<String, Object>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		return new DefaultKafkaConsumerFactory<String, String>(config);
	}

	//messages template consumer 
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> messagesKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(messageConsumerFactory());
		return factory;
	}


	//user template consumer config
	@Bean
	public ConsumerFactory<String, User> userConsumerFactory() {
		Map<String, Object> config = new HashMap<String, Object>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<String, User>(config, new StringDeserializer(), new JsonDeserializer<User>(User.class));
	}

	//user template consumer
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<String, User>();
		factory.setConsumerFactory(userConsumerFactory());
		return factory;
	}

	//---------------------------------
	//	
	//		PRODUCER TEMPLATES
	//
	//---------------------------------

	//message template producer
	@Bean
	public ProducerFactory<String, String> messageProducerFactory(){
		Map<String, Object> config = new HashMap<String, Object>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		return new DefaultKafkaProducerFactory<>(config);
	}

	@Bean
	public KafkaTemplate<String, String>messageKafkaTemplate(){
		return new KafkaTemplate<>(messageProducerFactory());
	}

	//user template producer
	@Bean
	public ProducerFactory<String, User> UserProducerFactory(){
		Map<String, Object> config = new HashMap<String, Object>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<>(config);
	}

	@Bean
	public KafkaTemplate<String, User>UserKafkaTemplate(){
		return new KafkaTemplate<>(UserProducerFactory());
	}

}

