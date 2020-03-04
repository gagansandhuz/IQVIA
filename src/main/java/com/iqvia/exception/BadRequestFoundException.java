package com.iqvia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestFoundException extends RuntimeException {

	private static final long serialVersionUID = 6084990780508644821L;

	public BadRequestFoundException(String value) {
		super(value);
	}

}