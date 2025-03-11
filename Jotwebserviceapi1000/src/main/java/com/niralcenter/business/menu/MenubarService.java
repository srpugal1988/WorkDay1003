package com.niralcenter.business.menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.niralcenter.business.model.Menu;


@Service
public class MenubarService {

		
		@Autowired
		MenubarRepository menubarRepository;
	
	
	   public List<Menu> loadMenuByrole(int roleid) {
	        List<Menu> menulist;
	        menulist =menubarRepository.loadMenuByrole(roleid);
	    	return menulist;
	    }
	    
	   
	   
}
