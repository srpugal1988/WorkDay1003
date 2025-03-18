package com.niralcenter.business.auth;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.niralcenter.business.common.ClientDefs;
import com.niralcenter.business.common.LoginInfo;
import com.niralcenter.business.common.ModuleDefs;
import com.niralcenter.business.common.ServerDefs;
import com.niralcenter.business.menu.MenubarService;
import com.niralcenter.business.model.Displayinfo;
import com.niralcenter.business.model.User;
import com.niralcenter.business.response.WSresponse;
import com.niralcenter.business.security.jwt.JWTHelper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




@Controller
@RequestMapping(value = "/auth")
//@CrossOrigin(origins = "http://localhost:4200/*")  
public class AuthenticationController {

	private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

	@Autowired
	AuthenticationService loginservice;

	@Autowired
	MenubarService menubarService;

	@Autowired
	WSresponse webfaceresponse;
	
	@Autowired
	Displayinfo displayinfo;
	
	
	@Autowired
	private JWTHelper jwthelper;
	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public WSresponse proceedLoginCheck(HttpSession httpSession,HttpServletRequest httpServletRequest,@RequestBody User user1) {
		logger.info("proceedLoginCheck method started");
		
		Map<String,Object> responseMap=new HashMap<String,Object>();
		
		String username=user1.getUsername();
		String password=user1.getPassword();
		
		
		User user=loginservice.loginCheck(username,password);
		String userAgent=httpServletRequest.getHeader("User-Agent");
		String ipAddress=httpServletRequest.getRemoteAddr();
		String remoteHost=httpServletRequest.getRemoteHost();
		
		
		if (user.getOnline()==1) {
			
			logger.info(
					">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.info("USER:(" + user.getUsername() + ") LOGGED IN SUCCESFULLY");
			logger.info("SESSION:(" + httpSession.getId() + ") CREATED.");
			logger.info(
					">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			user.setGlobalId(httpSession.getId());
			user.setUseragent(userAgent);
			user.setRemoteHost(remoteHost);
			user.setIpAddress(ipAddress);
			
			
			loginservice.UserLogForLogin(user);

			
			LoginInfo.user=user;

			
			String token = jwthelper.generateToken(user);
			
			
			displayinfo.setClient(ServerDefs.CLIENT);
			displayinfo.setVersion(ServerDefs.VERSION);
			displayinfo.setDisplayname(user.getFullname());
			displayinfo.setRolename(user.getRolename());

			
			httpSession.setAttribute(ServerDefs.SESSION_USER_LABEL, user);
			
			
			LoginInfo.USERS_SESSIONS.put(httpSession.getId(),user);
			
			
			responseMap.put("UserInfo",user);
			responseMap.put("DisplayInfo",displayinfo);
			responseMap.put("jwttoken",token);
			
			
			webfaceresponse.setCode("100");
			webfaceresponse.setMessage("LoggedIn Successfully");
			webfaceresponse.setPocket(responseMap);
		} else {
			LoginInfo.user=null;
			logger.info("USER:(" + user.getUsername() + ") LOGGED IN FAILURE");
			webfaceresponse.setCode("99");
			webfaceresponse.setMessage("Invalid Username or password");
			webfaceresponse.setPocket(user);
		}

		logger.info("proceedLoginCheck method completed");
		return webfaceresponse;
	}

	
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public WSresponse proceedLogout(HttpSession  httpSession,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,@RequestParam("globalId") String globalId) throws IOException {
		logger.info("proceedLogout method started");
		
		
		Object user_session = httpSession.getAttribute(ServerDefs.SESSION_USER_LABEL);
		User user = (User) user_session;
		
		user=LoginInfo.USERS_SESSIONS.get(globalId);
		
		if (user!=null) {
			logger.info(
					"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			logger.info("USER:(" + user.getUsername() + ") LOGGED-OUT SUCCESSFULLY");
			logger.info("SESSION:(" + globalId + ") REMOVED.");
			logger.info(
					"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			
			httpSession.removeAttribute(ServerDefs.SESSION_USER_LABEL);
			httpSession.invalidate();
			loginservice.UserLogForLogout(user);
			
			LoginInfo.user=null;
			
			LoginInfo.USERS_SESSIONS.remove(globalId);
			
			webfaceresponse.setCode("100");
			webfaceresponse.setMessage("USER LOGGED-OUT SUCCESSFULLY");
			webfaceresponse.setPocket("");

		} else {
			logger.info("USER NOT LOGGED IN YET");
			webfaceresponse.setCode("99");
			webfaceresponse.setMessage("USER NOT-LOGGED IN YET");
			webfaceresponse.setPocket("");
		}

		
		logger.info("proceedLogout method completed");
		return webfaceresponse;
	}
	
	
	
	@RequestMapping(value = "/checkForPageAccess", method = RequestMethod.GET)
	@ResponseBody
	public WSresponse checkForPageAccess(HttpSession httpSession,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,@RequestParam("moduleindex") String moduleindex,@RequestParam("globalId") String globalId) throws IOException {
		logger.info("checkForPageAccess method started");
		
		
		boolean isLoggedIn=true;
		boolean isPageAcessEligible=false;
		
		
		User user=LoginInfo.USERS_SESSIONS.get(globalId);
		
	
			
		if(user!=null) {
			isPageAcessEligible=loginservice.checkForPageAccess(user.getRoleid(),Integer.parseInt(moduleindex));
		}
		else {
			isLoggedIn=false;
		}
		
		
		if(isLoggedIn==false) {
			webfaceresponse.setCode("98");
			webfaceresponse.setMessage("LOG-IN INFORMATION NOT FOUND, KINDLY LOGIN");
			webfaceresponse.setPocket("");
		}
		else if(isPageAcessEligible==false) {
			webfaceresponse.setCode("97");
			webfaceresponse.setMessage("YOU DONT HAVE PERMISSION TO ACCESS THIS PAGE");
			webfaceresponse.setPocket("");
		}
		else if(isPageAcessEligible==true) {
			webfaceresponse.setCode("100");
			webfaceresponse.setMessage("YOU HAVE ACCESS TO THIS PAGE");
			webfaceresponse.setPocket("");
		}
		else {
			webfaceresponse.setCode("99");
			webfaceresponse.setMessage("SOME PROBLEM FOUND ON SERVER SIDE");
			webfaceresponse.setPocket("");
		}
		
		
		logger.info("checkForPageAccess method completed");
		return webfaceresponse;
	}
	
	
	
	@RequestMapping(value = "/jwtExpire")
	@ResponseBody
	public WSresponse jwtExpiredSupport(HttpSession  httpSession,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,@RequestParam("globalId") String globalId) {
		logger.info("jwtExpiredSupport method started");
		
		User user=LoginInfo.USERS_SESSIONS.get(globalId);
		
		if (user!=null) {
					
			httpSession.removeAttribute(ServerDefs.SESSION_USER_LABEL);
			httpSession.invalidate();
			loginservice.UserLogForLogout(user);
			
			LoginInfo.user=null;
			
			LoginInfo.USERS_SESSIONS.remove(globalId);
			
			webfaceresponse.setCode("96");
			webfaceresponse.setMessage("YOUR JWT TOKEN VALIDATION FAILED, KINDLY LOGIN AGAIN...");
			webfaceresponse.setPocket("");

			
		} else {
			webfaceresponse.setCode("98");
			webfaceresponse.setMessage("LOG-IN INFORMATION NOT FOUND, KINDLY LOGIN");
			webfaceresponse.setPocket("");
		}
		

		logger.info("jwtExpiredSupport method completed");
		return webfaceresponse;
	}
	

}
