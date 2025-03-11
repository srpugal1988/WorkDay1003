package com.niralcenter.business.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.niralcenter.business.auth.AuthenticationService;


@Component
public class CommonService {

	
	@Autowired
	AuthenticationService authenticationService;
	
	
	public boolean checkUsernameAlreadyFoundInDb(String username) {
    	boolean isUseravailable=false;
		isUseravailable=authenticationService.isUserFoundInDB(username);
		return isUseravailable;
    }

	
}
