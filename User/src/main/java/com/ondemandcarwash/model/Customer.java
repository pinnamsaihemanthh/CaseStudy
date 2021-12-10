package com.ondemandcarwash.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Customer {
	
	@Id
	private int cId;
	private String cName;
	private String cPhone;
	private String cEmail;
	private String cAddress;
	private String cPassword;
	
	//to String
	@Override
	public String toString() {
		return "Customer [cId=" + cId + ", cName=" + cName + ", cPhone=" + cPhone + ", cEmail=" + cEmail + ", cAddress="
				+ cAddress + ", cPassword=" + cPassword + "]";
	}
	
	//default constructor or no parameter constructor
	public Customer() {
		
	}
	
	//parameterised constructor
	public Customer(int cId, String cName, String cPhone, String cEmail, String cAddress, String cPassword) {
		super();
		this.cId = cId;
		this.cName = cName;
		this.cPhone = cPhone;
		this.cEmail = cEmail;
		this.cAddress = cAddress;
		this.cPassword = cPassword;
	}
	
	//getters and setters
	
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getcPhone() {
		return cPhone;
	}
	public void setcPhone(String cPhone) {
		this.cPhone = cPhone;
	}
	public String getcEmail() {
		return cEmail;
	}
	public void setcEmail(String cEmail) {
		this.cEmail = cEmail;
	}
	public String getcAddress() {
		return cAddress;
	}
	public void setcAddress(String cAddress) {
		this.cAddress = cAddress;
	}
	public String getcPassword() {
		return cPassword;
	}
	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}
	

}
