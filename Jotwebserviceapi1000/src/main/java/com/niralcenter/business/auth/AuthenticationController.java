package com.niralcenter.business.auth;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.niralcenter.business.model.WSresponse;

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
	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public WSresponse proceedLoginCheck(HttpSession httpSession,@RequestBody User user1) {

		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//String currentPrincipalName = authentication.getName();
		Map<String,Object> responseMap=new HashMap<String,Object>();
		
		String username=user1.getUsername();
		String password=user1.getPassword();
		
		
		User user=loginservice.loginCheck(username,password);
		
		
		//User user = loginservice.FetchUserInformation(usercredin.getUsername());
		
		
		if (user.getOnline()==1) {
			
			logger.info(
					">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.info("USER:(" + user.getUsername() + ") LOGGED IN SUCCESFULLY");
			logger.info("SESSION:(" + httpSession.getId() + ") CREATED.");
			logger.info(
					">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			user.setGlobalId(httpSession.getId());
			
			
			loginservice.UserLogForLogin(user);

			
			LoginInfo.user=user;
			
			
			displayinfo.setClient(ServerDefs.CLIENT);
			displayinfo.setVersion(ServerDefs.VERSION);
			displayinfo.setDisplayname(user.getFullname());
			displayinfo.setRolename(user.getRolename());

			
			httpSession.setAttribute(ServerDefs.SESSION_USER_LABEL, user);
			
			
			LoginInfo.USERS_SESSIONS.put(httpSession.getId(),user);
			
			
			responseMap.put("UserInfo",user);
			responseMap.put("DisplayInfo",displayinfo);
			
			
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

		return webfaceresponse;
	}

	
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void proceedLogout(HttpSession  httpSession,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,@RequestParam("id") String globalId) throws IOException {

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
			

		} else {
			logger.info("USER NOT LOGGED IN YET");
		}

		
		httpServletResponse.sendRedirect(ClientDefs.CLIENT_URL+"/"+ModuleDefs.LOGIN);
	}
	
	
	
	@RequestMapping(value = "/checkLoginUser", method = RequestMethod.GET)
	@ResponseBody
	public WSresponse checkForPageAccess(HttpSession httpSession,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,@RequestParam("moduleindex") String moduleindex,@RequestParam("id") String globalId) throws IOException {

		User user=LoginInfo.USERS_SESSIONS.get(globalId);
		
		
		
		boolean isLoggedIn=false;
		boolean isPageAcessEligible=false;
			
		if(user!=null) {
				
				isPageAcessEligible=loginservice.checkForPageAccess(user.getRoleid(),Integer.parseInt(moduleindex));
				
				displayinfo.setClient(ServerDefs.CLIENT);
				displayinfo.setVersion(ServerDefs.VERSION);
				displayinfo.setDisplayname(user.getFullname());
				displayinfo.setRolename(user.getRolename());
				
				webfaceresponse.setCode("100");
				webfaceresponse.setMessage("UserInformation Available!");
				webfaceresponse.setPocket(displayinfo);
				
				isLoggedIn=true;
	
		}else {
			isLoggedIn=false;
		}
		
		if(isLoggedIn==false) {
			displayinfo.setClient("");
			displayinfo.setVersion("");
			displayinfo.setDisplayname("");
			displayinfo.setRolename("");
			
			webfaceresponse.setCode("99");
			webfaceresponse.setMessage("UserInformation Not Available!");
			webfaceresponse.setPocket(displayinfo);
		}
		
		if(isPageAcessEligible==false) {
			webfaceresponse.setCode("98");
			webfaceresponse.setMessage("You are not having access to this page");
			webfaceresponse.setPocket(displayinfo);
		}
		
		
		return webfaceresponse;
	}
	

}
