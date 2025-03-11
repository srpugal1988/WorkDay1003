package com.niralcenter.business.auth;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.niralcenter.business.common.LoginInfo;
import com.niralcenter.business.model.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SimpleFilter implements Filter {

	

		    @Override
		    public void init(FilterConfig filterConfig) throws ServletException {
		       // System.out.println("init filter");
		    }

		    
		    @Override
		    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		     
		    	
		    	/*
		    	HttpServletRequest req = (HttpServletRequest) request;
		        HttpServletResponse res = (HttpServletResponse) response;
		        Cookie[] allCookies = req.getCookies();
		        
		        
		        if (allCookies != null) {
		            Cookie session = Arrays.stream(allCookies).filter(x -> x.getName().equals("JSESSIONID")).findFirst().orElse(null);

		            if (session != null) {
		                session.setHttpOnly(true);
		                session.setSecure(false);
		                res.addCookie(session);
		            }
		            
		        }*/
		    	
		        chain.doFilter(request, response);
		    }

		    
		    @Override
		    public void destroy() {
		       // System.out.println("destroy filter");
		    }
		


}
