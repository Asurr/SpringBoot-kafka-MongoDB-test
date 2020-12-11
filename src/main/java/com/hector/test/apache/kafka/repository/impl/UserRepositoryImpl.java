package com.hector.test.apache.kafka.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.hector.test.apache.kafka.model.User;
import com.hector.test.apache.kafka.model.Zodiac;
import com.hector.test.apache.kafka.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository{

	private final MongoOperations mongoOperations;
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	private Zodiac zodiac;


	//Instanciacion del UserRepositoryImpl
	@SuppressWarnings("deprecation")
	@Autowired
	public UserRepositoryImpl(MongoOperations mongoOperations,MongoTemplate mongoTemplate) {
		Assert.notNull(mongoOperations);
		this.mongoOperations = mongoOperations;
		this.mongoTemplate = mongoTemplate;
	}

	//metodos de busqueda
	@Override
	public Optional<User> findByDni(String dni) { //utilizo proyeciones con mongotemplate para no descargar todos los datos solo los que quiero
		Query query = new Query(); 
		query.fields().include("name").exclude("dni");  //creo una queri con mongotemplate y añado los campos que quiero y excluyo los que quiero
		ArrayList<User> users = (ArrayList<User>) mongoTemplate.find(query.addCriteria(Criteria.where("_id").is(dni)), User.class); //le añado un criteria de que busque por dni
		Optional<User> optionalUsers = Optional.ofNullable(!users.isEmpty()?users.get(0):null);
		return optionalUsers;
	}

	@Override
	public Optional<List<User>> findByName(String name) {
		ArrayList<User> users = (ArrayList<User>) this.mongoOperations.find(new Query(Criteria.where("name").is(name)), User.class);
		Optional<List<User>> optionalUsers = Optional.ofNullable(!users.isEmpty()?users:null);
		return optionalUsers;
	}

	@Override
	public Optional<List<User>> findByDept(String dept) {
		ArrayList<User> users = (ArrayList<User>) this.mongoOperations.find(new Query(Criteria.where("dept").is(dept)), User.class);
		Optional<List<User>> optionalUsers = Optional.ofNullable(!users.isEmpty()?users:null);
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
		return (User) findByDni(user.getDni()).get();
	}
	
	//metodo de update
	@Override
	public User update(User user) {
		this.mongoOperations.save(user);
		return (User) findByDni(user.getDni()).get();
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

	@Override
	public Optional<List<User>> findAllMatches(User user) {
		String [] compatibility;
		Optional<List<User>> optionalUsers = null;
		if(user.getBirthdate()!=null) {
			Zodiac zodicUser = zodiac.get(user.getBirthdate().getDay(), user.getBirthdate().getMonth());
			compatibility = zodicUser.getCompatibility();
			Criteria dateCriteria = new Criteria().andOperator(Criteria.where("age").lt(user.getAge()+7).gt(user.getAge()-7)).and("zodiac").in(compatibility).and("dni").not().is(user.getDni());
			ArrayList<User> users = (ArrayList<User>) this.mongoOperations.find(new Query(dateCriteria), User.class);
			optionalUsers = Optional.ofNullable(!users.isEmpty()?users:null);
		}
		return optionalUsers;
	}

}
