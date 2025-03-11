package com.niralcenter.business.module;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niralcenter.business.model.Business;
import com.niralcenter.business.model.User;
import com.niralcenter.business.model.WSresponse;



//@CrossOrigin(origins = "http://localhost:4200/jotwebface1000/*")
@Controller
public class BusinessApiController {	

	
	  private static final Logger logger = LogManager.getLogger(BusinessApiController.class);
	 
	  /*
	  @Autowired
	  AuthenticationService authenticationService;  
	  */
	
	  @Autowired
	  BusinessApiService businessApiService;
	  
	  
	  @Autowired
	  WSresponse wsresponse;
	  
	  
	  //0 
	  @PostMapping(value="/checklogin",produces = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<WSresponse> doLoginCheck(@RequestBody User user) {
		  logger.info("login check started");
	        boolean status=false;  
		  try {
	    	
			//status=authenticationService.CheckLoginCredintials(user);
	    	
			
			if(status) {
		    	wsresponse.setCode("100");
		    	wsresponse.setPocket(null);
		    	wsresponse.setMessage("Login successfully");
		    	return new ResponseEntity<>(wsresponse, HttpStatus.CREATED);
			}
			else {
				wsresponse.setCode("99");
		    	wsresponse.setPocket(null);
		    	wsresponse.setMessage("Invalid username or password");
		    	return new ResponseEntity<>(wsresponse, HttpStatus.UNAUTHORIZED);
			}
	    	
	    	
	    	//return new ResponseEntity<>("Business was created successfully"}", HttpStatus.CREATED);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	
	    	wsresponse.setCode("66");
	    	wsresponse.setPocket(null);
	    	wsresponse.setMessage(e.getMessage());
	      return new ResponseEntity<>(wsresponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  } 
	  
	  
	 //1
	  @PostMapping(value="/business/add",produces = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<WSresponse> createBusiness(@RequestBody Business business) {
	    try {
	    	
	    	businessApiService.createBusiness(business);
	    	
	    	wsresponse.setCode("100");
	    	wsresponse.setPocket(null);
	    	wsresponse.setMessage("Business was created successfully");
	    	return new ResponseEntity<>(wsresponse, HttpStatus.CREATED);
	    	
	    	
	    	//return new ResponseEntity<>("Business was created successfully"}", HttpStatus.CREATED);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	
	    	wsresponse.setCode("66");
	    	wsresponse.setPocket(null);
	    	wsresponse.setMessage(e.getMessage());
	      return new ResponseEntity<>(wsresponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  

	  //2
	  @GetMapping("/business/fetchall")
	  public ResponseEntity<WSresponse> getAllBusiness() {
		  List<Business> businesslist = new ArrayList<Business>();
		  
	    try {
	      businesslist=businessApiService.getAllBusiness();
	      /*
	      if (businesslist.isEmpty()) {
	          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
         */
	      wsresponse.setCode("100");
	      wsresponse.setPocket(businesslist);
	      wsresponse.setMessage("Business was fetched successfully");
	      return new ResponseEntity<>(wsresponse, HttpStatus.OK);
	      
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	
	    	wsresponse.setCode("66");
	    	wsresponse.setPocket(null);
	    	wsresponse.setMessage(e.getMessage());
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  
	  //3
	  @GetMapping("/business/fetch/{id}")
	  public ResponseEntity<WSresponse> getBusinessById(@PathVariable("id") int id) {
		  Business business=null;
		  /*Tutorial tutorial = tutorialRepository.findById(id);*/
	    try {
	    	
	    	/*
			if (business != null) {
				return new ResponseEntity<>(business, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} */
	    	
	    	business=businessApiService.getBusinessById(id);
	    			
	    	 wsresponse.setCode("100");
		     wsresponse.setPocket(business);
		     wsresponse.setMessage("Business was fetched successfully");
		     return new ResponseEntity<>(wsresponse, HttpStatus.OK);
		     
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
	    	wsresponse.setCode("66");
	    	wsresponse.setPocket(null);
	    	wsresponse.setMessage(e.getMessage());
	    	
	        return new ResponseEntity<>(wsresponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	  }

	  
	  //4
	  @PutMapping("/business/modify/{id}")
	  public ResponseEntity<WSresponse> updateBusiness(@PathVariable("id") int id, @RequestBody Business business) {
	    
		  try {
			Business _business = null;
			_business = businessApiService.getBusinessById(id);
			_business.setId(id);
			_business.setOwnername(business.getOwnername());
			_business.setBusinessname(business.getBusinessname());
			_business.setBusinesstype(business.getBusinesstype());
			_business.setEmail(business.getEmail());
			_business.setPhonenumber(business.getPhonenumber());
			_business.setPassword(business.getPassword());
			
			businessApiService.updateBusiness(_business);
			
			
			wsresponse.setCode("100");
			wsresponse.setPocket(business);
			wsresponse.setMessage("Business was updated successfully");
			return new ResponseEntity<>(wsresponse, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			wsresponse.setCode("66");
			wsresponse.setPocket(null);
			wsresponse.setMessage(e.getMessage());
			
			return new ResponseEntity<>(wsresponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	  }

	  
	  //5
	  @DeleteMapping("/business/remove/{id}")
	  public ResponseEntity<WSresponse> deleteBusiness(@PathVariable("id") int id) {
			 int result =0;
		  try {
	    
	         result = businessApiService.deleteBusiness(id);
	      
	      if (result == 0) {
	    	     wsresponse.setCode("100");
			     wsresponse.setPocket(result);
			     wsresponse.setMessage("Cannot find Business with id=" + id);
	             return new ResponseEntity<>(wsresponse,HttpStatus.OK);
	      }
	      
	         wsresponse.setCode("100");
		     wsresponse.setPocket(result);
		     wsresponse.setMessage("Business was deleted successfully");
	         return new ResponseEntity<>(wsresponse, HttpStatus.OK);
	         
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	 wsresponse.setCode("66");
			 wsresponse.setPocket(result);
			 wsresponse.setMessage(e.getMessage());
		     return new ResponseEntity<>(wsresponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  
	  //6
	  @DeleteMapping("/business/removeall")
	  public ResponseEntity<WSresponse> deleteAllBusiness() {
	    try {
	    	int numRows =0;
	    	
	    	numRows=businessApiService.deleteAllBusiness();
	    	
	    	 wsresponse.setCode("100");
			 wsresponse.setPocket(null);
			 wsresponse.setMessage("Deleted " + numRows + " Business(s) successfully.");
			 
	      return new ResponseEntity<>(wsresponse,HttpStatus.OK);
	    } catch (Exception e) {
	    	 e.printStackTrace();
	    	
	    	 wsresponse.setCode("66");
			 wsresponse.setPocket(null);
			 wsresponse.setMessage(e.getMessage());
		     return new ResponseEntity<>(wsresponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	  }

	  
	  //7
	  @GetMapping("/business/find/{businessname}")
	  public ResponseEntity<WSresponse> searchByBusinessName(@PathVariable("businessname") String businessname) {
	    try {
	    	List<Business> businesslist = null;
	    	
	    	businesslist=businessApiService.searchByBusinessName(businessname);
	    	
	    	 wsresponse.setCode("100");
			 wsresponse.setPocket(businesslist);
			 wsresponse.setMessage("Business was searched successfully");
	      return new ResponseEntity<>(wsresponse, HttpStatus.OK);
	      
	    } catch (Exception e) {
	    	 e.printStackTrace();
		   	 wsresponse.setCode("66");
			 wsresponse.setPocket(null);
			 wsresponse.setMessage(e.getMessage());
		 
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  
}
