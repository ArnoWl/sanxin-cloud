package com.sanxin.cloud.exception;

public class LoginOutException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -100509897248249450L;


	public LoginOutException(String arg0){
		super(arg0);
	}

	public LoginOutException(){
		super();
	}

	public LoginOutException(Throwable arg0){
		super(arg0);
	}

}
