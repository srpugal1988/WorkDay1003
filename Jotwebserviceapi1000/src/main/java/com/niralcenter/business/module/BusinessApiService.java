package com.niralcenter.business.module;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.niralcenter.business.model.Business;


@Service
public class BusinessApiService {

	
	   @Autowired
	   BusinessApiRepository businessApiRepository;
	   

	  public int createBusiness(Business business) {
		  int result=0;
		  try {
	    	result=businessApiRepository.save(business);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		return result;
	  }
	  

	  public List<Business> getAllBusiness() {
		  List<Business> businesslist= new ArrayList<Business>();
	      try {
			  businesslist=businessApiRepository.fetchAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      return businesslist;
	  }

	  
	  public Business getBusinessById(int id) {
		  Business business = null;
		  try {
			business = businessApiRepository.fetchById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return business;
	  }

	  
	  public int updateBusiness(Business business) {
		  int result = 0;
		  try {
			result = businessApiRepository.update(business);
		} catch (Exception e) {
			e.printStackTrace();
		}
			return result;
	  }

	  
	  public int deleteBusiness(int id) {
	    	int result=0 ;
	        try {
				result = businessApiRepository.deleteById(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
	  }

	  
	  public int deleteAllBusiness() {
		  int numRows = 0; 
	    try {
	         numRows = businessApiRepository.deleteAll();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	     return numRows;
	  }

	  
	  public List<Business> searchByBusinessName(String businessname) {
		  List<Business> businesslist = null;
	    try {
	    	businesslist = businessApiRepository.searchByBusinessName(businessname);
	    } catch (Exception e) {
	       	e.printStackTrace();
	    }
	    return businesslist;
	  }
	  
	  
}
