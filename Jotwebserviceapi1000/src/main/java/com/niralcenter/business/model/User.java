package com.niralcenter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class User extends SessionInformation implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	int id;
	String userrefno;
	String firstname;
	String lastname;
	String fullname;
	String email;
	String contactnumber;
	String country;
	int roleid;
	String rolename;
	String jobtitle;
	String username;
	String password;
	int online;				//1-Online 0-Offline
	boolean isauthorized;
	String globalId;
	
	
	public User() {
		
	}
	
	
	public User(int id, String userrefno, String firstname, String lastname, String fullname, String email,
			String contactnumber, String country, int roleid, String rolename, String jobtitle, String username,
			String password, int online, boolean isauthorized, String globalId) {
		super();
		this.id = id;
		this.userrefno = userrefno;
		this.firstname = firstname;
		this.lastname = lastname;
		this.fullname = fullname;
		this.email = email;
		this.contactnumber = contactnumber;
		this.country = country;
		this.roleid = roleid;
		this.rolename = rolename;
		this.jobtitle = jobtitle;
		this.username = username;
		this.password = password;
		this.online = online;
		this.isauthorized = isauthorized;
		this.globalId = globalId;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUserrefno() {
		return userrefno;
	}


	public void setUserrefno(String userrefno) {
		this.userrefno = userrefno;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getContactnumber() {
		return contactnumber;
	}


	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public int getRoleid() {
		return roleid;
	}


	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}


	public String getRolename() {
		return rolename;
	}


	public void setRolename(String rolename) {
		this.rolename = rolename;
	}


	public String getJobtitle() {
		return jobtitle;
	}


	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
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


	public int getOnline() {
		return online;
	}


	public void setOnline(int online) {
		this.online = online;
	}


	public boolean isIsauthorized() {
		return isauthorized;
	}


	public void setIsauthorized(boolean isauthorized) {
		this.isauthorized = isauthorized;
	}


	public String getGlobalId() {
		return globalId;
	}


	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}


	
	
    
	

}
