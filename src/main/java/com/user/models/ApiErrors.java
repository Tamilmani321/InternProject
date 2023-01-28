package com.user.models;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrors {
	
	String message;
	String details;
	HttpStatus httpStatus;
	
	

}
