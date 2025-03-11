package com.niralcenter.business.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.niralcenter.business.model.User;


@Repository
public class JdbcLoginRepository {

	public JdbcLoginRepository() {
		// TODO Auto-generated constructor stub
	}

	  @Autowired
	  private JdbcTemplate jdbcTemplate;
	  
	  
	 public boolean CheckLoginCredintials(User user) {
		// TODO Auto-generated method stub
		boolean result=false;
		String query="SELECT COUNT(*) FROM sm_userdetails WHERE username=? AND PASSWORD=?";
		int count = jdbcTemplate.queryForObject(query,
		          Integer.class, new Object[] { user.getUsername(),user.getPassword() });
		 
		 if(count>0)
			 result=true;
		 
	      return result;
	}

}
