package com.hector.test.apache.kafka.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.hector.test.apache.kafka.repository.ErrorRepository;

@Repository
public class ErrorRepositoryImpl implements ErrorRepository {

	private final MongoOperations mongoOperations;
	
	@Autowired
	public ErrorRepositoryImpl(MongoOperations mongoOperations) {
		Assert.notNull(mongoOperations);
		this.mongoOperations = mongoOperations;
	}
	
	@Override
	public Optional<List<Error>> findAll() {
		List<Error> errors = this.mongoOperations.find(new Query(),Error.class);
		Optional<List<Error>> optionalErrors = Optional.ofNullable(errors);
		return optionalErrors;
	}

	@Override
	public Optional<List<Error>> findByTopic(String topic) {
		ArrayList<Error> errors = (ArrayList<Error>) this.mongoOperations.find(new Query(Criteria.where("topic").is(topic)), Error.class);
		Optional<List<Error>> optionalErrors = Optional.ofNullable(!errors.isEmpty()?errors:null);
		return optionalErrors;
	}

	@Override
	public Optional<List<Error>> findByMessage(String message) {
		ArrayList<Error> errors = (ArrayList<Error>) this.mongoOperations.find(new Query(Criteria.where("message").is(message)), Error.class);
		Optional<List<Error>> optionalErrors = Optional.ofNullable(!errors.isEmpty()?errors:null);
		return optionalErrors;
	}

	@Override
	public Error save(Error error) {
		this.mongoOperations.save(error);
		return (Error) findByMessage(error.getMessage()).get();
	}

	@Override
	public void deleteAll() {
		this.mongoOperations.findAllAndRemove(new Query(), Error.class);		
	}

	@Override
	public void deleteByMessage(String message) {
		this.mongoOperations.findAndRemove(new Query(Criteria.where("message").is(message)), Error.class);			
	}

}
