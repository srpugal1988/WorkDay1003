package com.niralcenter.business.module;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.niralcenter.business.model.Business;


@Repository
public class JdbcBusinessRepository implements BusinessApiRepository{

	  @Autowired
	  private JdbcTemplate jdbcTemplate;

	  
	@Override
	public int save(Business business) {
		// TODO Auto-generated method stub
		int result=0;
		String query="INSERT INTO businessentry (ownername, businessname, businesstype,email,phonenumber,password) VALUES(?,?,?,?,?,?)";
		result=jdbcTemplate.update(query,
			        new Object[] {business.getOwnername(),business.getBusinessname(),business.getBusinesstype(),business.getEmail(),business.getPhonenumber(),business.getPassword()});
		return result;
	}

	
	@Override
	public List<Business> fetchAll() {
		// TODO Auto-generated method stub
		String query="SELECT * from businessentry";
		List<Business> businesslist=jdbcTemplate.query(query,BeanPropertyRowMapper.newInstance(Business.class));
		return businesslist;
	}
	
	
	@Override
	public Business fetchById(int id) {
		// TODO Auto-generated method stub
		 Business business = jdbcTemplate.queryForObject("SELECT * FROM businessentry WHERE id=?",
	          BeanPropertyRowMapper.newInstance(Business.class), id);
	      return business;
	}
	
	
	@Override
	public int update(Business business) {
		// TODO Auto-generated method stub
		int result=0;
		String query="UPDATE businessentry SET ownername=?, businessname=?, businesstype=?,email=?,phonenumber=?,password=? WHERE id=?";
		result=jdbcTemplate.update(query,
	            new Object[] {business.getOwnername(),business.getBusinessname(),business.getBusinesstype(),business.getEmail(),business.getPhonenumber(),business.getPassword(),business.getId()});
		return result;
	}

	@Override
	public int deleteById(int id) {
		 int result=0;
		 String query="DELETE FROM businessentry WHERE id=?";
		 result=jdbcTemplate.update(query, id);
		return result;
	}

	
	@Override
	public int deleteAll() {
		int result=0;
		result=jdbcTemplate.update("DELETE from businessentry");
		return result;
	}
	
	
	@Override
	public List<Business> searchByBusinessName(String businessname) {
		 String q = "SELECT * from businessentry WHERE businessname LIKE '%" + businessname + "%'";
		 List<Business> businesslist=jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Business.class));
		 return businesslist;
	}

	  
}
