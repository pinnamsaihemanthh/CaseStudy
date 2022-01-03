package com.ondemandcarwash.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "washers")
public class Washer {

	@Id
	private int Id;
	private String username;
	private String email;
	private String phone;
	private String address;
	private String password;

	// No parameter Constructor or default constructor
	public Washer() {

	}

	// to string
	@Override
	public String toString() {
		return "Washer [Id=" + Id + ", username=" + username + ", email=" + email + ", phone=" + phone + ", address="
				+ address + ", password=" + password + "]";
	}

	// parameterised constructor

	public Washer(int id, String username, String email, String phone, String address, String password) {
		super();
		Id = id;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.address = address;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
