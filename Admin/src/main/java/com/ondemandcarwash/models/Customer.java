package com.ondemandcarwash.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Customer {
	
	@Id
	private int cId;
	private String username;
	private String phone;
	private String email;
	private String address;
	private String password;

	
	//Parameterized constructor
	
	public Customer(int cId, String username, String phone, String email, String address, String password) {
		super();
		this.cId = cId;
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.password = password;
	}
	
	//default constructor or no parameter constructor
	public Customer() {
		
	}
	
	//to String
	@Override
	public String toString() {
		return "Customer [cId=" + cId + ", username=" + username + ", phone=" + phone + ", email=" + email
				+ ", address=" + address + ", password=" + password + "]";
		
		
	}
	
	//getters and setters
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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

	