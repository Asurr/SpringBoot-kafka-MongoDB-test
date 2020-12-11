package com.hector.test.apache.kafka.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Document(collection = "users")
@JsonPropertyOrder({"dni", "name", "dept", "age", "mail", "country", "phone","games","birthdate","zodiac"})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Zodiac zodiacModel;
	
	@Id
	private String dni;
	private String name,dept, mail,country,sex;
	private int age;
	private Date birthdate; 
	private String zodiac;
	private double phone;
	private List<Game> games;

	public User(String dni, String name, String dept) {
		this.dni = dni;
		this.name = name;
		this.dept = dept;
	}

	public User(String dni, String name, String dept, int age, String mail, String country, double phone,Date date,String sex) {
		super();
		this.dni = dni;
		this.name = name;
		this.dept = dept;
		this.age = age;
		this.mail = mail;
		this.country = country;
		this.phone = phone;
		this.birthdate = date;
		this.zodiac = zodiacModel.get(date.getDay(),date.getMonth()).name();
		this.sex= sex;
	}

	public User() {
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getZodiac() {
		return zodiac;
	}

	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getPhone() {
		return phone;
	}

	public void setPhone(double phone) {
		this.phone = phone;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "User [dni="+ dni +"name=" + name + ", dept=" + dept + "]";
	}

}
