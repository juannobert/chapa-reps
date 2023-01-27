package br.com.reps.services.exceptions;

import org.springframework.validation.FieldError;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private FieldError fieldError;
	
	public ValidationException(FieldError fieldError,String msg) {
		super(msg);
		this.fieldError = fieldError;
	
	}
}
