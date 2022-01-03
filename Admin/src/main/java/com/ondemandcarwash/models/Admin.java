package com.ondemandcarwash.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admins")
public class Admin {

	@Id
	private int Id;
	private String username;
	private String password;

	// non parameterised constructor
	public Admin() {

	}

	// to string

	@Override
	public String toString() {
		return "Admin [Id=" + Id + ", username=" + username + ", password=" + password + "]";
	}

	// parameterised constructor
	public Admin(int id, String username, String password) {
		super();
		Id = id;
		this.username = username;
		this.password = password;
	}
	// getters and setters

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
