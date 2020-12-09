package com.hector.test.apache.kafka.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hector.test.apache.kafka.exception.UserNotFoundException;
import com.hector.test.apache.kafka.model.Game;
import com.hector.test.apache.kafka.model.User;
import com.hector.test.apache.kafka.repository.GameRepository;
import com.hector.test.apache.kafka.repository.UserRepository;
import com.hector.test.apache.kafka.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepository userRepository;
	private GameRepository gameRepository;


	//Instanciacion del userRepository (Test Mockito repository para probar)
	@Autowired
	public UserServiceImpl(UserRepository userRepository,GameRepository gameRepository){
		this.userRepository = userRepository;
		this.gameRepository = gameRepository;
	}

	//metodos de busqueda
	public User findByDni(String dni) {
		Optional<User> optionalUser = userRepository.findByDni(dni);
		if(optionalUser.isPresent()) {
			LOGGER.info(String.format("Read userId '{}'", dni));
			return optionalUser.get();
		}else {
			throw new UserNotFoundException(String.format("User with dni '{}' not Found",dni));
		}
	}

	public List<User> findByName(String name) {
		Optional<List<User>> optionalUsersList = userRepository.findByName(name);
		if(optionalUsersList.isPresent()) {
			LOGGER.info(String.format("Read name '{}'", name));
			return optionalUsersList.get();
		}else {
			throw new UserNotFoundException(String.format("Users with name '{}' not Found",name));
		}
	}

	public List<User> findByDept(String dept) {
		Optional<List<User>> optionalUsersList = userRepository.findByDept(dept);				
		if(optionalUsersList.isPresent()) {
			LOGGER.info(String.format("Read dept '{}'", dept));
			return optionalUsersList.get();
		}else {
			throw new UserNotFoundException(String.format("Users with dept '{}' not Found",dept));
		}
	}

	public List<User> findAll() {
		Optional<List<User>> optionalUsersList = userRepository.findAll();
		if(optionalUsersList.isPresent()) {		
			try (Stream<User> result =  optionalUsersList.get().stream()) {
				result.forEach((final User user)-> LOGGER.info(String.format("User "+user.toString())));
			} 
			return optionalUsersList.get();
		}else {
			throw new UserNotFoundException(String.format("Users not Found"));
		}
	}

	//metodo de guardado (guarda los juegos tb)
	public User saveUser(User user) {
		if(!user.getGames().isEmpty()) {
			for (Game ga : user.getGames()) {
				if(!gameRepository.findByName(ga.getName().toLowerCase().trim()).isPresent()){
					gameRepository.save(ga);
				}
			}
		}
		return userRepository.save(user);
	}

	//metodo de update
	public User updateUser(User user) {
		return userRepository.update(user);
	}

	//metodos de borrado
	public void deleteUserByDni(String dni) {
		userRepository.deleteUserByDni(dni);
	}

	public void deleteUserByName(String name) {
		userRepository.deleteUserByName(name);
	}

	@Override
	public void deleteAll() {
		userRepository.deleteAll();
		
	}

}
