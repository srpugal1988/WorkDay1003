package com.niralcenter.business.settings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.niralcenter.business.common.LoginInfo;
import com.niralcenter.business.model.ModuleDetails;
import com.niralcenter.business.model.Role;
import com.niralcenter.business.model.RoleRightsDetails;


@Repository
public class RolesRepository {

	
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    
    public int StoreRolesInformation(Role role) {
    	Timestamp dateadded = new Timestamp(System.currentTimeMillis());
    	String createdby=LoginInfo.USERNAME;
    	String query=" INSERT INTO sm_roledetails(rolename,createdby) "
    			     + " VALUES "
    			     + "(?,?)";
    	System.out.println("Query:"+query);
    	int rowCount=jdbcTemplate.update(query,new Object[] {role.getRolename(),createdby});	
    	return rowCount;
    }
    
    
    public void AssignModulesRightsForRole(String rolename) {
    	
		List<ModuleDetails> modulelist=new ArrayList<ModuleDetails>();
		RowMapper<ModuleDetails> mapper=new RowMapper<ModuleDetails>() {
			@Override
			public ModuleDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				ModuleDetails md=new ModuleDetails();
				md.setId(rs.getInt("id"));
				md.setModulename(rs.getString("modulename"));
				md.setLevel(rs.getInt("level"));
				md.setModuleid(rs.getInt("moduleid"));
				md.setOrderid(rs.getInt("orderid"));
				return md;
			}
		};
		modulelist=jdbcTemplate.query("select * from sm_moduledetails",mapper);
		
		
		
		
		//fetching roleid
		int roleid=jdbcTemplate.queryForObject("select roleid from sm_roledetails where rolename=?",new Object[]{rolename},Integer.class);
		
		
		
		
		RoleRightsDetails rrd;
		for(ModuleDetails md:modulelist) {
			rrd=new RoleRightsDetails();
			
			rrd.setRoleid(roleid);
			rrd.setRights(0);
			rrd.setId(md.getId());
			rrd.setModulename(md.getModulename());
			rrd.setLevel(md.getLevel());
			rrd.setModuleid(md.getModuleid());
			rrd.setOrderid(md.getOrderid());
			
			
			String query=" INSERT INTO sm_rolesrightdetails(roleid,rights,id,modulename,level,moduleid,orderid) "
   			     + " VALUES "
   			     + "(?,?,?,?,?,?,?)";
			System.out.println("Query:"+query);
			int rowCount=jdbcTemplate.update(query,new Object[] {rrd.getRoleid(),rrd.getRights(),rrd.getId(),rrd.getModulename(),rrd.getLevel(),rrd.getModuleid(),rrd.getOrderid()});	
		}
		
	}
    
    
    public int ChangeAccessForModule(RoleRightsDetails roleright) {
    	Timestamp dateadded = new Timestamp(System.currentTimeMillis());
    	String createdby=LoginInfo.USERNAME;
    	int rights=0;
    	
    	if(roleright.getRights()==1) {
    		rights=0;
    	}
    	else {
    		rights=1;
    	}
    	
    	String query="UPDATE sm_rolesrightdetails set rights=? where id=? and roleid=?";
    	//System.out.println("rights:"+rights+"id:"+roleright.getId()+"roleid:"+roleright.getRoleid());
    	//System.out.println("Query:"+query);
    	int rowCount=jdbcTemplate.update(query,new Object[] {rights,roleright.getId(),roleright.getRoleid()});
    	
    	if(roleright.getLevel()==1 || roleright.getLevel()==2) {
    		
    		 query="UPDATE sm_rolesrightdetails set rights=? where roleid=? and moduleid=?";
    		 rowCount=jdbcTemplate.update(query,new Object[] {rights,roleright.getRoleid(),roleright.getId()});
    	}
    	
    	
    	return rowCount;
    }
    
    
    public List<Role> fetchAllRolesInformationFromDb() {
		List<Role> rolelist=new ArrayList<Role>();
		RowMapper<Role> mapper=new RowMapper<Role>() {
			@Override
			public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Role role=new Role();
				role.setRoleid(rs.getInt("roleid"));
				role.setRolename(rs.getString("rolename"));
				return role;
			}
		};
		rolelist=jdbcTemplate.query("select * from sm_roledetails",mapper);
		return rolelist;
	}
    
    
    
    public List<RoleRightsDetails> fetchRolesAndRightsInformation(int roleid) {
 		List<RoleRightsDetails> rolelist=new ArrayList<RoleRightsDetails>();
 		RowMapper<RoleRightsDetails> mapper=new RowMapper<RoleRightsDetails>() {
 			@Override
 			public RoleRightsDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
 				// TODO Auto-generated method stub
 				RoleRightsDetails rolerights=new RoleRightsDetails();
 				rolerights.setRoleid(rs.getInt("roleid"));
 				rolerights.setRights(rs.getInt("rights"));
 				rolerights.setId(rs.getInt("id"));
 				rolerights.setModulename(rs.getString("modulename"));
 				rolerights.setLevel(rs.getInt("level"));
 				rolerights.setModuleid(rs.getInt("moduleid"));
 				rolerights.setOrderid(rs.getInt("orderid"));
 				return rolerights;
 			}
 		};
 		rolelist=jdbcTemplate.query("select * from sm_rolesrightdetails where roleid=? order by id",new Object[] {roleid},mapper);
 		return rolelist;
 	}
    
}
