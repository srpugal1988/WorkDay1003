package com.niralcenter.business.auth;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niralcenter.business.common.LoginInfo;
import com.niralcenter.business.exception.UserAuthenticationFailedException;
import com.niralcenter.business.exception.UserNameMismatchException;
import com.niralcenter.business.model.PrUser;
import com.niralcenter.business.model.User;




@Service
public class AuthenticationService {
	
	
	private static final Logger logger = LogManager.getLogger(AuthenticationService.class);

	
	@Autowired
	AuthenticationRepository loginRepository;
	
	
	
	public User UserLogForLogin(User user) {
		loginRepository.loggingForLogin(user);
		return user;
	}
	
	
	public User UserLogForLogout(User user) {
		loginRepository.loggingForLogout(user);
		return user;
	}
	
	
	
	public User FetchUserInformation(String username) {
		User user=loginRepository.loadUserByname(username);
		return user;
	}
	
	
	
	public List<PrUser> loadPrincipalUsers() {
		    List<PrUser> PrUserlist=loginRepository.loadPrincipalUsers();
	    	return PrUserlist;
	}
	

	
	
	public User loginCheck(String username,String password) {
			logger.info("logincheck() method has been started");
			User user=new User();
			boolean isUseravailable=false;
			boolean isUserAuthenticated=false;
			isUseravailable=isUserFoundInDB(username);
			isUserAuthenticated=loginRepository.isCredintialsCheckInDB(username, password);
			if(isUseravailable) {
				if(isUserAuthenticated) {
					user=loginRepository.getUserInformation(username, password);
					this.UserLogForLogin(user);
	        		 LoginInfo.user=user;
					 user.setIsauthorized(true);
					 user.setOnline(1);
				}
				else {
					user.setOnline(0);
				    user.setIsauthorized(false);
				    LoginInfo.user=null;
					LoginInfo.USERNAME="";
					LoginInfo.ROLENAME="";
				}
			}
			else {
				    user.setOnline(0);
					LoginInfo.user=null;
				    user.setIsauthorized(false);
					LoginInfo.USERNAME="";
					LoginInfo.ROLENAME="";
			}
		   //Logging-Exceptions	
		   try {
			
				if(!isUseravailable) {
					throw new UserNameMismatchException(username);
				}
				 if(!isUserAuthenticated) {
					throw new UserAuthenticationFailedException(username);
				}
			}
			catch(Exception e) {
				 //e.printStackTrace();
			     logger.error(e);
			}
			
		   logger.info("logincheck() method has been completed");
			return user;
			
	}
	
	
    public boolean isUserFoundInDB(String username) {
    	boolean isUseravailable=false;
		isUseravailable=loginRepository.isUserFoundInDB(username);
		return isUseravailable;
    }
    
    
    public boolean checkForPageAccess(int roleid,int moduleindex) {
    	boolean isUseravailable=false;
    	moduleindex= (moduleindex==1)?1000:moduleindex;
		isUseravailable=loginRepository.checkForPageAccess(roleid, moduleindex);
		return isUseravailable;
    }

}
