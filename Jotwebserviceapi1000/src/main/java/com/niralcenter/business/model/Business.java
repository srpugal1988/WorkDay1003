package com.niralcenter.business.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class Business implements Serializable {

	public Business() {
		// TODO Auto-generated constructor stub
	}
	
	int id;
    String ownername;
    String businessname;
    String businesstype;
    String email;
    String phonenumber;
    String password;
   
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOwnername() {
		return ownername;
	}
	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}
	public String getBusinessname() {
		return businessname;
	}
	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
