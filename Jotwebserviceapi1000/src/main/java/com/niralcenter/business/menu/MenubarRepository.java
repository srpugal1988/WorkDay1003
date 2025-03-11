package com.niralcenter.business.menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.niralcenter.business.model.Menu;


@Repository
public class MenubarRepository {

	
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    
    
	
    public List<Menu> loadMenuByrole(int roleid) {
    	boolean isUserValidationResult=false;
    	@SuppressWarnings("deprecation")

        RowMapper<Menu> mapper = new RowMapper<Menu>() {
        	@Override
            public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
        		Menu menu = new Menu();
        	
        		menu.setRoleid(rs.getInt("roleid"));
        		menu.setRights(rs.getInt("rights"));
        		menu.setId(rs.getInt("id"));
        		menu.setModulename(rs.getString("modulename"));
        		menu.setLevel(rs.getInt("level"));
        		menu.setModuleid(rs.getInt("moduleid"));
        		menu.setOrderid(rs.getInt("orderid"));
      
                return menu;
            }
        };
        
        List<Menu> menulist=jdbcTemplate.query("select * from sm_rolesrightdetails where roleid=?",new Object[] {roleid},mapper);
    	return menulist;
    }
    
    
}
