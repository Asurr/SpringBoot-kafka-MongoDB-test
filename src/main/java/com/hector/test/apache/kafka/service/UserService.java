package com.hector.test.apache.kafka.service;

import java.util.List;

import com.hector.test.apache.kafka.model.User;

public interface UserService {

	public User findById(String id);

	public List<User> findByName(String name);

	public List<User> findByDept(String dept);

	public List<User> findAll();

	public User saveUser(User user);

	public User updateUser(User user);

	public void deleteUserById(String id);

	public void DeleteUserByName(String name);

}
