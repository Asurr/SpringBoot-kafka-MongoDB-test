package com.hector.test.apache.kafka.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hector.test.apache.kafka.exception.ErrorNotFoundException;
import com.hector.test.apache.kafka.repository.ErrorRepository;
import com.hector.test.apache.kafka.service.ErrorService;

@Service("errorService")
@Transactional
public class ErrorServiceImpl implements ErrorService {

	private ErrorRepository errorRepository;

	@Autowired
	public ErrorServiceImpl(ErrorRepository errorRepository) {
		this.errorRepository = errorRepository;
	}

	public List<Error> findErrors(){
		Optional<List<Error>> errors = errorRepository.findAll();
		if(errors.isPresent()) {
			return errors.get();
		}else {
			throw new ErrorNotFoundException(String.format("Not errors found")); 
		}
	}

	public List<Error> findByMessage(String message){
		Optional<List<Error>> errors = errorRepository.findByMessage(message);
		if(errors.isPresent()) {
			return errors.get();
		}else {
			throw new ErrorNotFoundException(String.format("Error with message '{}' not Found",message)); 
		}
	}
	
	public List<Error> findByTopic(String topic){
		Optional<List<Error>> errors = errorRepository.findByTopic(topic);
		if(errors.isPresent()) {
			return errors.get();
		}else {
			throw new ErrorNotFoundException(String.format("Error with topic '{}' not Found",topic)); 
		}
	}

	public Error saveError(Error error){
		return errorRepository.save(error);		
	}

	public void deleteErrorByMessage(String code){
		errorRepository.deleteByMessage(code);
	}

	public void deleteAll(){
		errorRepository.deleteAll();
	}

}
