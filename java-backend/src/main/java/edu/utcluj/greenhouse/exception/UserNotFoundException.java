package edu.utcluj.greenhouse.exception;

public class UserNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("Username or password not matching!");
		// TODO Auto-generated constructor stub
	}

}
