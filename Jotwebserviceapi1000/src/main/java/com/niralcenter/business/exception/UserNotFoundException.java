package com.niralcenter.business.exception;

public class UserNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String message;
	
	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String msg) {
		super(msg);
		this.message=msg;
	}
}
