package com.niralcenter.business.model;

import java.util.Collection;

import org.springframework.stereotype.Component;


@Component
public class PrUser {

	public PrUser() {
		// TODO Auto-generated constructor stub
	}
	
	String username;
	String password;
	String role;
	
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
