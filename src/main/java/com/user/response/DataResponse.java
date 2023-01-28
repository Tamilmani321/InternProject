package com.user.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse {

	private int statusCode;
	private HttpStatus httpStatus;
	private boolean status;
	private String message;
	private Object data;
}
