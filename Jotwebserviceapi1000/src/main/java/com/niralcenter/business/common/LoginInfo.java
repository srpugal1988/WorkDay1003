package com.niralcenter.business.common;

import java.util.HashMap;
import java.util.Map;

import com.niralcenter.business.model.User;

public class LoginInfo {

	public static String USERNAME;
	public static String ROLENAME;
	public static String DBNAME;
	public static String CLIENTNAME;
	public static User user;
	
	public static Map<String,User> USERS_SESSIONS=new HashMap<String,User>();
	
}
