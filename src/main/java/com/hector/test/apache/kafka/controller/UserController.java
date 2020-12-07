package com.hector.test.apache.kafka.controller;

import java.util.List;

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
import com.hector.test.apache.kafka.service.UserService;
import com.hector.test.apache.kafka.service.impl.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Users", description = "lets make create,delete or update users")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


	private final UserService usersService;
	private User user;
	private List<User> users;

	@Autowired
	public UserController(UserService usersService) {
		this.usersService = usersService;
	}

	//---------------------------------
	//	
	//	  FIND CONTROLLER END POINTS
	//
	//---------------------------------

	@ApiOperation(value = "Find an user by Dni", notes = "Return a user by Dni" )
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Generic Error"),
			@ApiResponse(code = 404, message = "User Not found") })
	@RequestMapping(value="/api/user/findByDni/{dni}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> findUserByDni(@PathVariable String dni)  throws  UserNotFoundException{
		LOGGER.info("Get userByDni");
		try{
			user = usersService.findByDni(dni);
		}catch(UserNotFoundException e){
			throw new UserNotFoundException("User not exist");        			
		}     
		return ResponseEntity.ok(user);

	}

	@ApiOperation(value = "Find users by Name", notes = "Return users by Name" )
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Generic Error"),
			@ApiResponse(code = 404, message = "Users Not found") })
	@RequestMapping(value="/api/user/findByName/{name}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> findUsersByName(@PathVariable String name)  throws  UserNotFoundException{
		LOGGER.info("Get userByName");
		try{
			users = usersService.findByName(name);
		}catch(UserNotFoundException e){
			throw new UserNotFoundException("Users not exist");        			
		}     
		return ResponseEntity.ok(users);

	}

	@ApiOperation(value = "Find an user by Dept", notes = "Return users by Dept" )
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Generic Error"),
			@ApiResponse(code = 404, message = "Users Not found") })
	@RequestMapping(value="/api/user/findByDept/{dept}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> FindUsersByDept(@PathVariable String dept)  throws  UserNotFoundException{
		LOGGER.info("Get userByDept");
		try{
		}catch(UserNotFoundException e){
			throw new UserNotFoundException("Users not exist");        			
		}     
		return ResponseEntity.ok(users);

	}

	@ApiOperation(value = "Find all user", notes = "Return all users" )
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Generic Error"),
			@ApiResponse(code = 404, message = "Users Not found") })
	@RequestMapping(value="/api/user/find",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> findAll(){
		LOGGER.info("Get allUsers");
		try {
			users = usersService.findAll();
		}catch (UserNotFoundException e) {
			throw new UserNotFoundException("Users not exist");        			
		}
		return ResponseEntity.ok(users);
	}

	//---------------------------------
	//	
	//	  DELETE CONTROLLER END POINTS
	//
	//-------------------------------

	@RequestMapping(value="/api/user/deleteByDni/{dni}",method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete an user", notes = "Delete a user by Dni")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Delete Success"),
			@ApiResponse(code = 500, message = "Generic Error"),
			@ApiResponse(code = 404, message = "User Not found") })
	public ResponseEntity<Void>deleteUserByDni(@PathVariable String dni){
		LOGGER.info("Delete user dni " + dni);
		usersService.deleteUserByDni(dni);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/api/user/deleteByName/{name}",method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete an user", notes = "Delete a user by Name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Delete Success"),
			@ApiResponse(code = 500, message = "Generic Error"),
			@ApiResponse(code = 404, message = "User Not found") })
	public ResponseEntity<Void>deleteUserByName(@PathVariable String name){
		LOGGER.info("Delete user name " + name);
		usersService.deleteUserByName(name);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/api/user/delete",method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete all users", notes = "Delete all users")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Delete Success"),
			@ApiResponse(code = 500, message = "Generic Error"),
			@ApiResponse(code = 404, message = "User Not found") })
	public ResponseEntity<Void>deleteAll(){
		LOGGER.info("Delete All users ");
		usersService.deleteAll();
		return ResponseEntity.noContent().build();
	}

	//---------------------------------
	//	
	//	  CREATE CONTROLLER END POINT
	//
	//-------------------------------

	@RequestMapping(value = "/api/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create an user", notes = "Create a new user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Create Success"),
			@ApiResponse(code = 500, message = "Generic Error"),
			@ApiResponse(code = 404, message = "User Not found") })
	public  ResponseEntity<User> saveUser(@RequestBody @Valid User user){
		LOGGER.info("Save new user");
		try {
			user = usersService.saveUser(user);
		}catch (UserNotFoundException e) {
			throw new UserNotFoundException("Users not exist");        			
		}
		return ResponseEntity.ok(user);
	}

}
