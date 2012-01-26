package it.gas.eeforum.exceptions;

public class InvalidIdException extends Exception {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Invalid ID!";
	}

	
	
}
