package com.niralcenter.business.model;

import org.springframework.stereotype.Component;

@Component
public class Displayinfo {

	public Displayinfo() {
		// TODO Auto-generated constructor stub
	}

	String displayname;
	String rolename;
	String version;
	String client;
	
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}

	
}
