package com.hector.test.apache.kafka.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mongodb.lang.NonNull;

@Document(collection = "users")
@JsonPropertyOrder({"userId", "name","dept"})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NonNull  
	private String id;
	@NonNull  
	private String name;
	private String dept;

	public User(String name, String dept) {
		this.name = name;
		this.dept = dept;
	}

	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", dept=" + dept + "]";
	}

}
