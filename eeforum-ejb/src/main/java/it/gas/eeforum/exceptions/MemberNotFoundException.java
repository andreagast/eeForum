package it.gas.eeforum.exceptions;

public class MemberNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The user does not exist.";
	}
	
}
