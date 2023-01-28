package com.user.location.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApiErrors {

	String messages;
	String detail;
	HttpStatus status;
	LocalDateTime timestamp;

}
