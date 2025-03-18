package com.niralcenter.business.menu;

import java.io.IOException;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.niralcenter.business.common.ClientDefs;
import com.niralcenter.business.common.LoginInfo;
import com.niralcenter.business.common.ModuleDefs;
import com.niralcenter.business.common.ServerDefs;
import com.niralcenter.business.model.Displayinfo;
import com.niralcenter.business.model.Menu;
import com.niralcenter.business.model.User;
import com.niralcenter.business.response.WSresponse;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




//@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class MenubarController {

	private static final Logger logger = LogManager.getLogger(MenubarController.class);
	
	@Autowired
	MenubarService menubarService;
	
	@Autowired
	Displayinfo displayinfo;
	
	@Autowired
	WSresponse wsresponse;
	
    //
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public WSresponse gotoIndexPage(HttpSession httpSession,HttpServletResponse httpServletResponse) throws IOException {

		System.out.println(">>>>>>>>>>>");
		/*String URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.LOGIN;
		httpServletResponse.sendRedirect(URL);
		*/
		wsresponse.setCode("100");
		wsresponse.setMessage("Jobwebserviceapi1000 running sucessfully");
		wsresponse.setPocket("");
		
		return wsresponse;
	}

	@RequestMapping(value = "/{pageindex}", method = RequestMethod.GET)
	public void gotoOtherPage(@PathVariable int pageindex, HttpSession httpSession,HttpServletResponse httpServletResponse) throws IOException {
		logger.info("#######################");

		String displayname = "";
		String rolename = "";
		ModelAndView mav;
		boolean loginflag=false;
		String URL="";

		
		logger.info("USER OPENING THE PAGE: " + pageindex);
		

		
		
		switch (pageindex) {
		

		// 1
		case 1:
			URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.LOGIN;
			break;

			
		// 1000
		case 1000:
			URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.HOME;
			break;

			
			
		// 2000
		case 2100:
			 URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.WORKDAY_BUSINESS_CREATE;
			break;
		case 2200:
			 URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.WORKDAY_BUSINESS_READ;
			break;
		case 2300:
			 URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.WORKDAY_BUSINESS_UPDATE;
			break;
		case 2400:
			 URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.WORKDAY_BUSINESS_DELETE;
			break;

			
				
		//3000
		case 3000:
			 URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.SETTINGS;
			break;
		case 3100:
			 URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.SETTINGS_USER;
			break;
		case 3200:
			 URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.SETTINGS_ROLE;
			 break;
		case 3300:
			 URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.SETTINGS_BROWSER;
			 break;
		case 3310:
			 URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.SETTINGS_BROWSER_SESSIONS;
			break;

			
		default:
			URL=ClientDefs.CLIENT_URL+"/"+ModuleDefs.DEFAULT;
			break;

		}
		
		
		if(loginflag) {
			
			displayinfo.setDisplayname(displayname);
			displayinfo.setRolename(rolename);
			displayinfo.setVersion(ServerDefs.VERSION);
			displayinfo.setClient(ServerDefs.CLIENT);
		}

		
		httpServletResponse.sendRedirect(URL);
	}
	
	
	
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<WSresponse> loadMenu(HttpSession httpSession,HttpServletResponse httpServletResponse,@RequestParam("globalId") String globalId) {
		List<Menu> menulist=null;
		int roleid=0;
	
		User user=LoginInfo.USERS_SESSIONS.get(globalId);
		
		if(user!=null)
		roleid=user.getRoleid();
		
		menulist=menubarService.loadMenuByrole(roleid);
		
		wsresponse.setCode("100");
	    wsresponse.setPocket(menulist);
	    wsresponse.setMessage("Menu was fetched successfully");
	      
	    return new ResponseEntity<>(wsresponse, HttpStatus.OK);
	}
	
}
