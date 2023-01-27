package br.com.reps.services.exceptions;

import org.springframework.validation.FieldError;

public class UserAlreadyExistsException extends ValidationException{

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(FieldError fieldError, String msg) {
		super(fieldError, msg);
	}

}
