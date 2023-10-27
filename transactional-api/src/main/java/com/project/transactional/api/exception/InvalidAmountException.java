package com.project.transactional.api.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidAmountException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public static final HttpStatus RETURN_STATUS = HttpStatus.BAD_REQUEST;

}
