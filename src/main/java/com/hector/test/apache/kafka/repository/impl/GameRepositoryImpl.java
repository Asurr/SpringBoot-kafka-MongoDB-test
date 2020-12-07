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

import com.hector.test.apache.kafka.model.Game;
import com.hector.test.apache.kafka.repository.GameRepository;

@Repository
public class GameRepositoryImpl implements GameRepository{
	
	private final MongoOperations mongoOperations;

	@Autowired
	public GameRepositoryImpl(MongoOperations mongoOperations) {
		Assert.notNull(mongoOperations);
		this.mongoOperations = mongoOperations;
	}

	@Override
	public Optional<List<Game>> findAll() {
		List<Game> games = this.mongoOperations.find(new Query(), Game.class);
		Optional<List<Game>> optionalGames = Optional.ofNullable(games);
		return optionalGames;
	}

	@Override
	public Optional<Game> findByCode(String code) {
		ArrayList<Game> game = (ArrayList<Game>) this.mongoOperations.find(new Query(Criteria.where("code").is(code)), Game.class);
		Optional<Game> optionalGame = Optional.ofNullable(!game.isEmpty()?game.get(0):null);
		return optionalGame;
	}

	@Override
	public Game save(Game game) {
		this.mongoOperations.save(game);
		return (Game) findByCode(game.getCode()).get();
	}

	@Override
	public void deleteByCode(String code) {
		this.mongoOperations.findAndRemove(new Query(Criteria.where("code").is(code)), Game.class);		
	}

	@Override
	public void deleteAll() {
		this.mongoOperations.findAllAndRemove(new Query(), Game.class);		
	}

}
