package com.user.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginRequest {
	
	@NotBlank(message = "Name should not be empty")
	@Size(min=5, message = "Name must contain atleast 5 characters")
	private String name;
	@NotBlank(message = "Password should not be empty")
	@Size(min=5, message = "Password must contain atleast 5 characters")
	private String password;

}
