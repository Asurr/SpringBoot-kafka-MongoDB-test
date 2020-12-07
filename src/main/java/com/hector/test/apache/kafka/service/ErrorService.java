package com.hector.test.apache.kafka.service;

import java.util.List;

public interface ErrorService {
	
	List<Error> findErrors();

	List<Error> findByMessage(String message);
	
	List<Error> findByTopic(String topic);

	Error saveError(Error error);

	void deleteErrorByMessage(String code);

	void deleteAll();

}
