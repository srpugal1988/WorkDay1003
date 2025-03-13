package com.niralcenter.business.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.niralcenter.business.common.LoginInfo;
import com.niralcenter.business.model.SessionInformation;
import com.niralcenter.business.model.User;

@Service
public class BrowserSessionService {

	
	private static final Logger logger = LogManager.getLogger(BrowserSessionService.class);
	
	
	public List<SessionInformation> fetchGlobalSessionInformation(){
		
		
		List<SessionInformation> globalSessionUsersList=new ArrayList<SessionInformation>();
		User user;
		SessionInformation sessioninfo;
		
		
		Map<String,User> GlobalSessions=LoginInfo.USERS_SESSIONS;
		Set<String> keys=GlobalSessions.keySet();
		
		
		logger.info(">>>>>>>>>>>>>>>>>>Total number of active sessions:"+keys.size());
				
		
		for(String key:keys) {
			user=new User();
			sessioninfo=new SessionInformation();
			
			user=GlobalSessions.get(key);
			
			sessioninfo.setUserId(user.getUserrefno());
			sessioninfo.setFullname(user.getFullname());
			sessioninfo.setSessionId(key);
			sessioninfo.setUseragent(user.getUseragent());
			sessioninfo.setRemoteHost(user.getRemoteHost());
			sessioninfo.setIpAddress(user.getIpAddress());
			
			globalSessionUsersList.add(sessioninfo);
		}
		
		return globalSessionUsersList;
	}
	
	
}
