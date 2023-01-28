package com.user.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class UnauthorizedErrorResponse {

	private boolean status;
	private int statusCode;
	private HttpStatus httpStatus;
	private Object message;
	
}
