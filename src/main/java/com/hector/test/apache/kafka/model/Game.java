package com.hector.test.apache.kafka.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Document(collection = "game")
@JsonPropertyOrder({"code","name","category"})
public class Game implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id	
	private String code;
	private String name;
	private String category;
	
	public Game() {
		super();
	}
	
	public Game(String code, String name, String category) {
		super();
		this.code = code;
		this.name = name;
		this.category = category;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Game [code=" + code + ", name=" + name + ", category=" + category + "]";
	}
	
}
