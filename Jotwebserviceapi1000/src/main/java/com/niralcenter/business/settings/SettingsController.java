package com.niralcenter.business.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.niralcenter.business.auth.AuthenticationController;
import com.niralcenter.business.auth.AuthenticationService;
import com.niralcenter.business.common.LoginInfo;
import com.niralcenter.business.common.ServerDefs;
import com.niralcenter.business.model.Role;
import com.niralcenter.business.model.RoleRightsDetails;
import com.niralcenter.business.model.SessionInformation;
import com.niralcenter.business.model.User;
import com.niralcenter.business.model.WSresponse;
import com.niralcenter.business.validations.UserValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/settingsctrl")
public class SettingsController {

	
	private static final Logger logger = LogManager.getLogger(SettingsController.class);
	
	
	@Autowired
	WSresponse webfaceresponse;
	
	
	@Autowired
	UserService userservice;
	
	
	@Autowired
	RolesService roleservice;
	
	
	@Autowired
	BrowserSessionService browserSessionService;
	
	
	@Autowired
	UserValidator uservalidator;
	
	
	@Autowired
	AuthenticationService loginservice;
	
	
	@RequestMapping(value="/user/store",method=RequestMethod.POST)
	@ResponseBody
	public WSresponse storeUserInformation(@RequestBody com.niralcenter.business.model.User User) {
		System.out.println("user.getUsername"+User.getUsername());
		Map<String,Object> retMap=new HashMap<String,Object>();
		
		
		retMap=userservice.StoreUserInformation(User);
		
		
		if(retMap.get("validationstatus").equals("success")) {
			webfaceresponse.setCode("100");
			webfaceresponse.setMessage("User register successfully!");
			webfaceresponse.setPocket(User);
		}else {
			webfaceresponse.setCode("99");
			webfaceresponse.setMessage("User register failed");
			webfaceresponse.setPocket(retMap);
		}
		
		
		return webfaceresponse;
	}
	
	
	@RequestMapping(value="/user/fetchall",method=RequestMethod.GET)
	@ResponseBody
	public WSresponse fetchAllUserInformation() {
		
		List<User> userlist=userservice.fetchAllUserInformation();
		
		Map<String,Object> response=new HashMap<String,Object>();
		response.put("userlist",userlist);
		
		webfaceresponse.setCode("100");
		webfaceresponse.setMessage("Data retrived successfully");
		webfaceresponse.setPocket(response);
		return webfaceresponse;
	}
	

	
	@RequestMapping(value="/user/getnextuserreferencenumber",method=RequestMethod.GET)
	@ResponseBody
	public WSresponse LoadNextUserReference() {
		
		String userrefnumber=userservice.LoadNextUserReference();
		
		
		Map<String,Object> response=new HashMap<String,Object>();
		response.put("userreferencenumber",userrefnumber);
		
		webfaceresponse.setCode("100");
		webfaceresponse.setMessage("User register successfully!");
		webfaceresponse.setPocket(response);
		return webfaceresponse;
	}
	
	
	@RequestMapping(value="/role/fetchall",method=RequestMethod.GET)
	@ResponseBody
	public WSresponse fetchAllRolesInformation() {
		List<Role> roleslist=roleservice.fetchRolesInformation();
		
		Map<String,Object> response=new HashMap<String,Object>();
		response.put("rolesinformationlist",roleslist);
		
		
		webfaceresponse.setCode("100");
		webfaceresponse.setMessage("Data retrived successfully");
		webfaceresponse.setPocket(response);
		return webfaceresponse;
	}
	
	
	
	@RequestMapping(value="/role/rights",method=RequestMethod.GET)
	@ResponseBody
	public WSresponse fetchRolesRightsInformation(@RequestParam(name = "roleid") int roleid) {
		List<RoleRightsDetails> rolerightslist=new ArrayList<RoleRightsDetails>();
		
		
		rolerightslist=roleservice.fetchRolesAndRightsInformation(roleid);
		
		
		Map<String,Object> mapinfo=new HashMap<String,Object>();
		mapinfo.put("rolesrightlist",rolerightslist);

		webfaceresponse.setCode("100");
		webfaceresponse.setMessage("Data retrived successfully");
		webfaceresponse.setPocket(mapinfo);
		return webfaceresponse;
	}
	
	
	
	@RequestMapping(value="/role/rightschange",method=RequestMethod.POST)
	@ResponseBody
	public WSresponse ChangeAccessForModule(@RequestBody RoleRightsDetails rolerights) {
		int updatecount=roleservice.ChangeAccessForModule(rolerights);

		webfaceresponse.setCode("100");
		webfaceresponse.setMessage("Data retrived successfully");
		webfaceresponse.setPocket(rolerights);
		return webfaceresponse;
	}
	
	
	
	@RequestMapping(value="/role/store",method=RequestMethod.POST)
	@ResponseBody
	public WSresponse StoreRolesInformation(@RequestBody Role role) {

		int count=roleservice.StoreRolesInformation(role);
		
		
		webfaceresponse.setCode("100");
		webfaceresponse.setMessage("Role added successfully!");
		webfaceresponse.setPocket(role);
		
		return webfaceresponse;
	}
	
	
	@RequestMapping(value="/browser/sessions/fetchall",method=RequestMethod.GET)
	@ResponseBody
	public WSresponse fetchGlobalSessionInformation() {
		
		
		List<SessionInformation> globalSessionUsersList=browserSessionService.fetchGlobalSessionInformation();
		
		
		webfaceresponse.setCode("100");
		webfaceresponse.setMessage("Data retrived successfully");
		webfaceresponse.setPocket(globalSessionUsersList);
		
		return webfaceresponse;
	}
	
	
	@RequestMapping(value="/browser/sessions/kill",method=RequestMethod.GET)
	@ResponseBody
	public WSresponse KillParticularSession(HttpSession  httpSession,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,@RequestParam("globalId") String globalId,@RequestParam("killerId") String killerId) {

		User user=LoginInfo.USERS_SESSIONS.get(killerId);
		
		if (user!=null) {
					
			httpSession.removeAttribute(ServerDefs.SESSION_USER_LABEL);
			httpSession.invalidate();
			loginservice.UserLogForLogout(user);
			
			LoginInfo.user=null;
			
			LoginInfo.USERS_SESSIONS.remove(killerId);
			
			webfaceresponse.setCode("100");
			webfaceresponse.setMessage("SELECTED SESSION HAS BEEN CANCELED");
			webfaceresponse.setPocket("");

		} else {
			logger.info("USER NOT LOGGED IN YET");
			webfaceresponse.setCode("99");
			webfaceresponse.setMessage("USER NOT-LOGGED IN YET");
			webfaceresponse.setPocket("");
		}
		

	
		return webfaceresponse;
	}
	
	
}
