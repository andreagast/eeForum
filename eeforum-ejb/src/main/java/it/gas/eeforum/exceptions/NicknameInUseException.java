package it.gas.eeforum.exceptions;

public class NicknameInUseException extends Exception {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The nickname is already in use!";
	}
	
}
