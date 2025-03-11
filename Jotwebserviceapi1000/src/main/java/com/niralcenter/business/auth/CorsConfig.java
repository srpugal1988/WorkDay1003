package com.niralcenter.business.auth;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer{
/*
	public CorsConfig() {
		// TODO Auto-generated constructor stub
	}
	
	 @Override
     public void addCorsMappings(CorsRegistry registry) {
		 System.out.println("Cors------------------->");
         registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
                 .allowedHeaders("*");
     }
     */
}
