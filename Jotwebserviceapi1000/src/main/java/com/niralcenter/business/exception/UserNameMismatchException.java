package com.niralcenter.business.exception;


public class UserNameMismatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
	
	
	public UserNameMismatchException() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public UserNameMismatchException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
		this.message=msg;
	}
}
