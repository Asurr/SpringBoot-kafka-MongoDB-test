package com.hector.test.apache.kafka.repository;

import java.util.List;
import java.util.Optional;

import com.hector.test.apache.kafka.model.User;

public interface UserRepository {

	public Optional<User> findById(String id);

	public Optional<List<User>> findByName(String name);

	public Optional<List<User>> findByDept(String dept);

	public Optional<List<User>> findAll();

	public User save(User user);

	public void deleteUserById(String id);

	void deleteUserByName(String name);

	public User update(User user);

}
