package com.project.transactional.api.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.account.api.exception.CustomExceptionHandler;

import jakarta.ws.rs.InternalServerErrorException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(OutputCaptureExtension.class)
public class CustomExceptionHandlerTest {

	private CustomExceptionHandler handler = new CustomExceptionHandler();

	@Test
	public void handleInternalServerErrorException_should_return_bad_request() {
		String message = "message";
		ResponseEntity<String> response = handler
				.handleInternalServerErrorException(new InternalServerErrorException(message));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(message, response.getBody());
	}

	@Test
	public void handleGenericException_should_return_bad_request() {
		String message = "message";
		ResponseEntity<String> response = handler.handleGenericException(new ArithmeticException(message));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(message, response.getBody());
	}

	@Test
	public void handleIllegalArgumentException_should_return_bad_request() {
		String message = "message";
		ResponseEntity<String> response = handler.handleGenericException(new IllegalArgumentException(message));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(message, response.getBody());
	}
}
