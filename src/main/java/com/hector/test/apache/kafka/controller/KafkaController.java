package com.hector.test.apache.kafka.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hector.test.apache.kafka.exception.UserNotFoundException;
import com.hector.test.apache.kafka.model.User;
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

	@Autowired
	private KafkaProducerService sender;

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
	public String sendMessage(@PathVariable String message) {
		String respuesta = "Exito";		
		try {
			sender.sendMessage("kafka_Example",message);
		}catch (Exception e) {
			// TODO: handle exception
			respuesta = "Error desconocido";
		}
		return respuesta;
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
			sender.sendUser("kafka_Example_json",user);
		}catch (Exception e) {
			throw new UserNotFoundException("Error User not sent");        			
		}
		return ResponseEntity.ok(user);
	}

}
