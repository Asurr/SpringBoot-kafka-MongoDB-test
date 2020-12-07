package com.hector.test.apache.kafka.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.hector.test.apache.kafka.model.User;
import com.hector.test.apache.kafka.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository{

	private final MongoOperations mongoOperations;

	//Instanciacion del UserRepositoryImpl
	@SuppressWarnings("deprecation")
	@Autowired
	public UserRepositoryImpl(MongoOperations mongoOperations) {
		Assert.notNull(mongoOperations);
		this.mongoOperations = mongoOperations;
	}

	//metodos de busqueda
	@Override
	public Optional<User> findByDni(String dni) {
		User users = (User) this.mongoOperations.find(new Query(Criteria.where("dni").is(dni)), User.class);
		Optional<User> optionalUsers = Optional.ofNullable(users);
		return optionalUsers;
	}

	@Override
	public Optional<List<User>> findByName(String name) {
		List<User> users = this.mongoOperations.find(new Query(Criteria.where("name").is(name)), User.class);
		Optional<List<User>> optionalUsers = Optional.ofNullable(users);
		return optionalUsers;
	}

	@Override
	public Optional<List<User>> findByDept(String dept) {
		List<User> users = this.mongoOperations.find(new Query(Criteria.where("dept").is(dept)), User.class);
		Optional<List<User>> optionalUsers = Optional.ofNullable(users);
		return optionalUsers;
	}

	@Override
	public Optional<List<User>> findAll() {
		List<User> users = this.mongoOperations.find(new Query(), User.class);
		Optional<List<User>> optionalUsers = Optional.ofNullable(users);
		return optionalUsers;
	}

	//metodo de guardado
	@Override
	public User save(User user) {
		this.mongoOperations.save(user);
		return (User) findByDni(user.getId()).get();
	}
	
	//metodo de update
	@Override
	public User update(User user) {
		this.mongoOperations.save(user);
		return (User) findByDni(user.getId()).get();
	}

	//metodos de borrado
	@Override
	public void deleteUserByDni(String dni) {
		this.mongoOperations.findAndRemove(new Query(Criteria.where("dni")), User.class);		
	}

	@Override
	public void deleteUserByName(String name) {
		this.mongoOperations.findAndRemove(new Query(Criteria.where("name").is(name)), User.class);		
	}

	@Override
	public void deleteAll() {
		this.mongoOperations.findAllAndRemove(new Query(), User.class);
	}

}
