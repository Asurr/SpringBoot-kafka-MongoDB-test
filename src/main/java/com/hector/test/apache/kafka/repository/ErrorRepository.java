package com.hector.test.apache.kafka.repository;

import java.util.List;
import java.util.Optional;

public interface ErrorRepository {

	Optional<List<Error>> findAll();
	
	Optional<List<Error>> findByTopic(String topic);

	Optional<List<Error>> findByMessage(String message);

	Error save(Error error);

	void deleteAll();

	void deleteByMessage(String code);

}
