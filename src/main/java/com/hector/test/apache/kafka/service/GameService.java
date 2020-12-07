package com.hector.test.apache.kafka.service;

import java.util.List;

import com.hector.test.apache.kafka.model.Game;

public interface GameService {

	public Game findByCode(String code);
	
	public List<Game> findAll();
	
	public Game save(Game game);
	
	public void deleteByCode(String code);
	
	public void deleteAll();
	
}
