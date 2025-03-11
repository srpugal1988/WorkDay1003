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
import com.niralcenter.business.model.User;


@Repository
public class UsersRepository {

	
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    
    public int StoreUserInformation(User user) {
    	Timestamp dateadded = new Timestamp(System.currentTimeMillis());
    	String createdby=LoginInfo.USERNAME;
    	int isactiveuser=1;
    	String dbname=LoginInfo.DBNAME;
        String clientname=LoginInfo.CLIENTNAME;
        
      //fetching rolename
      	String rolename=jdbcTemplate.queryForObject("select rolename from sm_roledetails where roleid=?",new Object[]{user.getRoleid()},String.class);
        user.setRolename(rolename);
   
    	String query=" INSERT INTO sm_userdetails(userrefno,firstname,lastname,fullname,email,contactnumber,roleid,"
    			+ "rolename,jobtitle,username,password,online,dateadded,createdby,isactiveuser,dbname,clientname) "
    			+ "VALUES "
    			+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	System.out.println("Query:"+query);
    	int rowCount=jdbcTemplate.update(query,new Object[] {user.getUserrefno(),user.getFirstname(),user.getLastname(),user.getFirstname()+" "+user.getLastname(),user.getEmail(),user.getContactnumber(),user.getRoleid(),user.getRolename(),user.getJobtitle(),user.getUsername(),user.getPassword(),+user.getOnline(),dateadded,createdby,isactiveuser,dbname,clientname});	
    	return rowCount;
    }
    
    
    
    public List<User> fetchAllUserInformationFromDb() {
		List<User> users=new ArrayList<User>();
		RowMapper<User> mapper=new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				User user=new User();
				user.setId(rs.getInt("id"));
			    user.setUserrefno(rs.getString("userrefno"));
			    user.setFirstname(rs.getString("firstname"));
			    user.setLastname(rs.getString("lastname"));
			    user.setFullname(rs.getString("fullname"));
			    user.setEmail(rs.getString("email"));
			    user.setContactnumber(rs.getString("contactnumber"));
			    user.setRoleid(rs.getInt("roleid"));
			    user.setRolename(rs.getString("rolename"));
			    user.setJobtitle(rs.getString("jobtitle"));
			    user.setUsername(rs.getString("username"));
			    user.setOnline(rs.getInt("online"));
				return user;
			}
		};
		users=jdbcTemplate.query("select * from sm_userdetails",mapper);
		return users;
	}
}
