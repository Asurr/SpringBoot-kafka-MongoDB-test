package com.hector.test.apache.kafka.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hector.test.apache.kafka.exception.GameNotFoundException;
import com.hector.test.apache.kafka.model.Game;
import com.hector.test.apache.kafka.repository.GameRepository;
import com.hector.test.apache.kafka.service.GameService;

@Service("gameService")
@Transactional
public class GameServiceImpl implements GameService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceImpl.class);
	private GameRepository gameRepository;
	
	public GameServiceImpl(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}
	
	public Game findByCode(String code) {
		Optional<Game> game =  gameRepository.findByCode(code);
		if(game.isPresent()) {
			LOGGER.info(String.format("Read game '{}'", code));
			return game.get();
		}else {
			throw new GameNotFoundException(String.format("Game with code '{}' not Found",code));
		}
	}
	
	public List<Game> findAll() {
		Optional<List<Game>> games =  gameRepository.findAll();
		if(games.isPresent()) {
			LOGGER.info(String.format("Read all games"));
			return games.get();
		}else {
			throw new GameNotFoundException(String.format("Games not Found"));
		}
	}
	
	public Game save(Game game) {
		return  gameRepository.save(game);	
	}
	
	public void deleteByCode(String code) {
		gameRepository.deleteByCode(code);	
	}
	
	public void deleteAll() {
		gameRepository.deleteAll();	
	}

}
