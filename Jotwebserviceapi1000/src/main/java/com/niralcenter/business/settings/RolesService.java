package com.niralcenter.business.settings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.niralcenter.business.model.Role;
import com.niralcenter.business.model.RoleRightsDetails;


@Service
public class RolesService {

	
	@Autowired
	RolesRepository rolesRepository;
	
	
    public int StoreRolesInformation(Role role) {
    	int rowCount=rolesRepository.StoreRolesInformation(role);
    	rolesRepository.AssignModulesRightsForRole(role.getRolename());
    	return rowCount;
    }
    
    public int ChangeAccessForModule(RoleRightsDetails roleright) {
    	int rowCount=rolesRepository.ChangeAccessForModule(roleright);
    	return rowCount;
    }
    
    
    public List<Role> fetchRolesInformation() {
		List<Role> roles=new ArrayList<Role>();
		roles=rolesRepository.fetchAllRolesInformationFromDb();
		return roles;
	}
    
    
    public List<RoleRightsDetails> fetchRolesAndRightsInformation(int roleid) {
 		List<RoleRightsDetails> rolerightslist=new ArrayList<RoleRightsDetails>();
 		rolerightslist=rolesRepository.fetchRolesAndRightsInformation(roleid);
 		return rolerightslist;
 	}

	
}
