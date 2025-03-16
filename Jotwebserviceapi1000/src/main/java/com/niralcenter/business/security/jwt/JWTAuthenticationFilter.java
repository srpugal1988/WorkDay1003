package com.niralcenter.business.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.niralcenter.business.common.LoginInfo;
import com.niralcenter.business.model.User;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    
    
    @Autowired
    private JWTHelper jwtHelper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Authorization=Bearer <token>

    	String hopeJwtParameter = request.getParameter("hopeJwt");
    	String requestMethod=request.getMethod();
        String requestHeader =""; 
        String sessionId     ="";
        String username = null;
        String token = null;
        Boolean validateToken=false;
        
        
        if(hopeJwtParameter.equals("No") || requestMethod.equals("OPTIONS")) {
        	hopeJwtParameter="No";
        	validateToken=true;
        }

        
        if(hopeJwtParameter.equals("Yes")) {
        		
        	    requestHeader=request.getHeader("Authorization");
        	    sessionId=request.getParameter("globalId");
        	    
        	    logger.info(" Header :  {}", requestHeader);
        	
		        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
		            //looking good
		            token = requestHeader.substring(7);
		            try {
		            	
		                username = this.jwtHelper.getUsernameFromToken(token);
			        	User user=LoginInfo.USERS_SESSIONS.get(sessionId);
			            validateToken = this.jwtHelper.validateToken(token,user);
		                
		            } catch (IllegalArgumentException e) {
		                logger.info("Illegal Argument while fetching the username !!");
		                validateToken=false;
		                e.printStackTrace();
		            } catch (ExpiredJwtException e) {
		                logger.info("Given jwt token is expired !!");
		                e.printStackTrace();
		                validateToken=false;
		            } catch (MalformedJwtException e) {
		                logger.info("Some changed has done in token !! Invalid Token");
		                validateToken=false;
		                e.printStackTrace();
		            } catch (Exception e) {
		            	validateToken=false;
		                e.printStackTrace();
		            }
		        } else {
		            logger.info("Invalid Header Value !! ");
		            validateToken=false;
		        }
			        
         }
        
        
        if(validateToken==false) {
        	  logger.error("====================JWT AUTHENTICATION FAILURE=============================");
        	  request.getRequestDispatcher("/auth/jwtExpire").forward(request,response);
        	  return;
        }
        
        filterChain.doFilter(request, response);
        
    }
}
