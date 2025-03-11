package com.niralcenter.business.exception;

public class UserAuthenticationFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	
	public UserAuthenticationFailedException() {
		// TODO Auto-generated constructor stub
		super();
	}

	public UserAuthenticationFailedException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
		this.message=msg;
	}
	
}
