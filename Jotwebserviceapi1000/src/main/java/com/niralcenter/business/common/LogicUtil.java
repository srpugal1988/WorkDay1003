package com.niralcenter.business.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogicUtil {

	
	public static String getNextUserReferenceNumber() {
		Date date = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyymmddhhmmss");  
	    String strdate= formatter.format(date);  
		String label="USREF";
		String userrefnumber=label+strdate;
		return userrefnumber;
	}
	
	
	
	
}
