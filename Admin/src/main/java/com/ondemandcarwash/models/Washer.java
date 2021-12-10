package com.ondemandcarwash.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "washers")
public class Washer {
	
	@Id
	private int wId;
	private String wName;
	private String wEmail;
	private String wPhone;
	private String wAddress;
	private String wPassword;
	
	
	//No parameter Constructor or default constructor
	public Washer() {
		
	}
	
	//to string 
	@Override
	public String toString() {
		return "Washer [wId=" + wId + ", wName=" + wName + ", wEmail=" + wEmail + ", wPhone=" + wPhone + ", wAddress="
				+ wAddress + ", wPassword=" + wPassword + "]";
	}
	
	//parameterised constructor
	public Washer(int wId, String wName, String wEmail, String wPhone, String wAddress, String wPassword) {
		super();
		this.wId = wId;
		this.wName = wName;
		this.wEmail = wEmail;
		this.wPhone = wPhone;
		this.wAddress = wAddress;
		this.wPassword = wPassword;
	}
	
	//getters and setters
	
	public int getwId() {
		return wId;
	}
	public void setwId(int wId) {
		this.wId = wId;
	}
	public String getwName() {
		return wName;
	}
	public void setwName(String wName) {
		this.wName = wName;
	}
	public String getwEmail() {
		return wEmail;
	}
	public void setwEmail(String wEmail) {
		this.wEmail = wEmail;
	}
	public String getwPhone() {
		return wPhone;
	}
	public void setwPhone(String wPhone) {
		this.wPhone = wPhone;
	}
	public String getwAddress() {
		return wAddress;
	}
	public void setwAddress(String wAddress) {
		this.wAddress = wAddress;
	}
	public String getwPassword() {
		return wPassword;
	}
	public void setwPassword(String wPassword) {
		this.wPassword = wPassword;
	}
	
	
	
	

}
