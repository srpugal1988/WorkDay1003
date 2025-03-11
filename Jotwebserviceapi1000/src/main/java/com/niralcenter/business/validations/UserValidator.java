package com.niralcenter.business.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.niralcenter.business.common.CommonService;
import com.niralcenter.business.model.User;


@Component
public class UserValidator  implements Validator {

	
	@Autowired
	CommonService commonService;
	
	
	@Autowired
	Environment env;
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		 return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		User u = (User) target;
		
        if (commonService.checkUsernameAlreadyFoundInDb(u.getUsername())) {
        	errors.rejectValue("username", env.getProperty("error.user.dublicateusername"));
        } else if (u.getPassword().equals("oldpassword")) {
        	errors.rejectValue("password", env.getProperty("error.user.invalidpassword"));
        }
		
	}

}
