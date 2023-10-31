package com.project.transactional.api.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.ws.rs.InternalServerErrorException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ InvalidAmountException.class })
	public ResponseEntity<String> handleInvalidAmount(InvalidAmountException ex) {
		return ResponseEntity.status(InvalidAmountException.RETURN_STATUS).body(ex.getMessage());
	}

	@ExceptionHandler({ InternalServerErrorException.class })
	public ResponseEntity<String> handleInternalServerErrorException(InternalServerErrorException ex) {
		return ResponseEntity.internalServerError().body(ex.getMessage());
	}

	@ExceptionHandler({ NoSuchElementException.class })
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		return ResponseEntity.internalServerError().body("An unexpected error ocurred, please, try again later.");
	}

}
