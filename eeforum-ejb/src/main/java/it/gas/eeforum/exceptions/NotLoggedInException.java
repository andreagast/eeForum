package it.gas.eeforum.exceptions;

public class NotLoggedInException extends Exception {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "User not logged in.";
	}
	
}
