package com.niralcenter.business.module;

import java.util.List;

import com.niralcenter.business.model.Business;




public interface BusinessApiRepository {

		  int save(Business business);
		  
		  List<Business> fetchAll();

		  Business fetchById(int id);
		  
		  int update(Business business);

		  int deleteById(int id);
		  
		  int deleteAll();

		  List<Business> searchByBusinessName(String businessname);

		  
}
