package com.hector.test.apache.kafka.service;

import java.util.List;

import com.hector.test.apache.kafka.model.User;

public interface UserService {

	public User findByDni(String dni);

	public List<User> findByName(String name);

	public List<User> findByDept(String dept);

	public List<User> findAll();

	public User saveUser(User user);

	public User updateUser(User user);

	public void deleteUserByDni(String dni);

	public void deleteUserByName(String name);
	
	public void deleteAll();

}
