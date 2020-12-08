package com.hector.test.apache.kafka.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hector.test.apache.kafka.exception.UserNotFoundException;
import com.hector.test.apache.kafka.model.User;
import com.hector.test.apache.kafka.service.KafkaConsumerService;
import com.hector.test.apache.kafka.service.KafkaProducerService;
import com.hector.test.apache.kafka.service.impl.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Kafka", description = "lets make create messages or user to send to kafka")
public class KafkaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	
	private KafkaProducerService kafkaProducerService;
	private KafkaConsumerService kafkaConsumerService;
	
	@Value("${message.topic.example:kafka_Example}")
	private String topicMessages;
	
	@Value("${message.topic.example.json:kafka_Example_json}")
	private String topicJson;
	
	@Autowired
	public KafkaController(KafkaProducerService kafkaProducerService ,  KafkaConsumerService kafkaConsumerService) {
		this.kafkaProducerService = kafkaProducerService;
		this.kafkaConsumerService = kafkaConsumerService;
		
	}

	//---------------------------------
	//	
	//	  SEND KAFKA CONTROLLER END POINTS
	//
	//-------------------------------

	//manda un mensaje de texto a kafka (topic por defecto kafka_Example)
	@ApiOperation(value = "Send message to kafka", notes = "Send message to kafka" )
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Generic Error"),
			@ApiResponse(code = 404, message = "message Not found") })
	@RequestMapping(value="/api/publish/{message}",method =  RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<String> sendMessage(@PathVariable String message) {
		String respuesta = "Exito";		
		try {
			kafkaProducerService.sendMessage(topicMessages,message);
		}catch (Exception e) {
			respuesta = "Error desconocido";
		}
		return ResponseEntity.ok(respuesta);
	}

	//manda un mensaje de usuario JSON a kafka (topic por defecto kafka_Example_json)
	@ApiOperation(value = "Send user to kafka", notes = "Send user to kafka" )
	@ApiResponses(value = { @ApiResponse(code = 200, message = "send Success"),
			@ApiResponse(code = 500, message = "Generic Error"),
			@ApiResponse(code = 404, message = "message Not found") })
	@RequestMapping(value="/api/publish/user",method =  RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> sendUser(@RequestBody @Valid User user) {
		LOGGER.info("Send new user");
		try {
			kafkaProducerService.sendUser(topicJson,user);
		}catch (Exception e) {
			throw new UserNotFoundException("Error User not sent");        			
		}
		return ResponseEntity.ok(user);
	}
	
	
	@ApiOperation(value = "Consume messages from kafka_Example topic last days", notes = "Consume messages kafka from kafka_Example topic" )
	@ApiResponses(value = { @ApiResponse(code = 200, message = "send Success"),
			@ApiResponse(code = 500, message = "Generic Error"),
			@ApiResponse(code = 404, message = "message Not found") })
	@RequestMapping(value = "/api/consume-message/{days}", method = { RequestMethod.GET })
	public ResponseEntity<List<String>> consumeMessageLastDays(@PathVariable long days) {
		List<String> messages;	
		try {
			messages = kafkaConsumerService.consumeTopicDays(topicMessages, days);
		}catch (Exception e) {
			messages = Collections.emptyList();
			}		
		return  ResponseEntity.ok(messages);
	}
	
	@ApiOperation(value = "Consume json users from kafka_Example topic last days", notes = "Consume json users kafka from kafka_Example topic" )
	@ApiResponses(value = { @ApiResponse(code = 200, message = "send Success"),
			@ApiResponse(code = 500, message = "Generic Error"),
			@ApiResponse(code = 404, message = "message Not found") })
	@RequestMapping(value = "/api/consume-json/{days}", method = { RequestMethod.GET })
	public ResponseEntity<List<User>> consumeJsonLastDays(@PathVariable long days) {
		List<User> users;
		try {
			users = kafkaConsumerService.consumeJsonTopicDays(topicJson, days);
		}catch (Exception e) {
			users = Collections.emptyList();
			}		
		return ResponseEntity.ok(users);
	}

}
