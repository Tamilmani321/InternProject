package com.user.advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.user.exception.AccessDeniedException;
import com.user.exception.CustomException;
import com.user.exception.RoleNotFoundException;
import com.user.response.ApriErroResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApriErroResponse> handleMissingParams(MissingServletRequestParameterException ex) {
	    ApriErroResponse response = new ApriErroResponse();
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setTimestamp(LocalDateTime.now());
		response.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	   
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> invalidArgument(MethodArgumentNotValidException ex){
		ApriErroResponse response = new ApriErroResponse();
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setTimestamp(LocalDateTime.now());
		Map<String, String> errorMap= new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error->{
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		response.setMessage(errorMap);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);		
	}
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> DataIntegrityViolationException(CustomException ex){
		ApriErroResponse response = new ApriErroResponse();
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setTimestamp(LocalDateTime.now());
		response.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);		
	}
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex){
		ApriErroResponse response = new ApriErroResponse();
		response.setHttpStatus(HttpStatus.UNAUTHORIZED);
		response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		response.setTimestamp(LocalDateTime.now());
		response.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);		
	}
	@ExceptionHandler( ConstraintViolationException.class )
	public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {

		Map<String, String> errorMap = new HashMap<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
		}

		ApriErroResponse apiError = new ApriErroResponse();
		apiError.setMessage(errorMap);
		apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
		apiError.setTimestamp(LocalDateTime.now());
		apiError.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);

	}
	
}
