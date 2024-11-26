package com.transaction.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidation {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> validationHandler(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<String, String>();
		List<FieldError> error = e.getBindingResult().getFieldErrors();

		for (FieldError err : error) {
			errors.put(err.getField(), err.getDefaultMessage());
		}

		return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(TransactionCustomException.class)
	public ResponseEntity<String> globalExceptionHandler(TransactionCustomException e) {

		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

	}

}
