package com.niralcenter.business.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;

import com.niralcenter.business.common.LogicUtil;
import com.niralcenter.business.model.User;
import com.niralcenter.business.validations.UserValidator;


@Service
public class UserService {

	
	@Autowired
	UsersRepository usersRepository;
	
	
	@Autowired
	UserValidator userValidator;
	
	
    public Map<String,Object> StoreUserInformation(User user) {
    	int rowCount=0;
    	Map<String,Object> retMap=new HashMap<String,Object>();
    	
    	//Validations
    	DataBinder binder = new DataBinder(user);
    	binder.setValidator(userValidator);
    	MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue("","");
    	binder.bind(propertyValues);
    	binder.validate();
    	BindingResult results = binder.getBindingResult();
    	
    	
    	List<FieldError> listfe=results.getFieldErrors();
    	Error error=new Error();
    	for(FieldError f:listfe) {
    		//error.setField(f.getField());
    		//error.setMessage(f.getCode());
    	}
    	
    	
    	if(!results.hasErrors()) {
    		rowCount=usersRepository.StoreUserInformation(user);
    		 retMap.put("error","");
    		 retMap.put("validationstatus","success");
    	 }else {
    		 rowCount=0;
    		 retMap.put("error",error);
    		 retMap.put("validationstatus","failure");
    	 }
    	
    	return retMap;
    }
    
	
    public List<User> fetchAllUserInformation() {
		List<User> users=new ArrayList<User>();
		users=usersRepository.fetchAllUserInformationFromDb();
		return users;
	}
    
	public String LoadNextUserReference() {
		String userrefnumber=LogicUtil.getNextUserReferenceNumber();	
		return userrefnumber;
	}
	
}
