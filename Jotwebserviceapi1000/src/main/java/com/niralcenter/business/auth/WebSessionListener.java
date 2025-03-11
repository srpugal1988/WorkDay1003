package com.niralcenter.business.auth;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.niralcenter.business.common.LoginInfo;

import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;




@Configuration
public class WebSessionListener {

	@Bean                           // bean for http session listener
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {               // This method will be called when session created
                System.out.println("Session Created with session id:" + se.getSession().getId());
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent se) {         // This method will be automatically called when session destroyed
                System.out.println("Session Destroyed, Session id:" + se.getSession().getId());
            	
            	LoginInfo.user=null;
            	LoginInfo.USERNAME="";
            	LoginInfo.ROLENAME="";
            	LoginInfo.DBNAME="";
            	LoginInfo.CLIENTNAME="";
            	
            }
        };
    }

    @Bean                   // bean for http session attribute listener
    public HttpSessionAttributeListener httpSessionAttributeListener() {
        return new HttpSessionAttributeListener() {
            @Override
            public void attributeAdded(HttpSessionBindingEvent se) {            // This method will be automatically called when session attribute added
              //  System.out.println("Attribute Added following information");
               // System.out.println("Attribute Name:" + se.getName());
               // System.out.println("Attribute Value:" + se.getValue());
            }

            @Override
            public void attributeRemoved(HttpSessionBindingEvent se) {      // This method will be automatically called when session attribute removed
                //System.out.println("attributeRemoved");
            }

            @Override
            public void attributeReplaced(HttpSessionBindingEvent se) {     // This method will be automatically called when session attribute replace
                //System.out.println("Attribute Replaced following information");
               // System.out.println("Attribute Name:" + se.getName());
               // System.out.println("Attribute Old Value:" + se.getValue());
            }
        };
    }
}
