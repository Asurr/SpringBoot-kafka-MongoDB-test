package com.hector.test.apache.kafka.repository;

import java.util.List;
import java.util.Optional;

import com.hector.test.apache.kafka.model.Game;

public interface GameRepository {
	
	Optional<List<Game>> findAll();

	Optional<Game> findByCode(String code);
	
	Optional<Game> findByName(String name);

	Game save(Game game);

	void deleteByCode(String code);

	void deleteAll();

}
