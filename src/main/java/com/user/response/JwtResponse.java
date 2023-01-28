package com.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	
	private int statusCode;
	private boolean status;
	private String message;
	private Object data;
	private String jwtToken;
	
}


