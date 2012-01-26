package it.gas.eeforum.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

	@Override
	public void initialize(Email arg0) {
	}

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		int atpos = arg0.indexOf('@');
		if (atpos == -1)
			return false;
		int dotpos = arg0.indexOf('.', atpos + 2);
		if (dotpos == -1)
			return false;
		//everything ok
		return true;
	}

}
