package com.user.location.dtos;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.entity.Roles;
import com.user.location.entities.Countries;

import lombok.Data;

@Data
public class CountryDto {

	private long countryId;
	@NotBlank(message = "Countryname should not be empty")
	@Size(min=5, message = "Countryname must contain atleast 5 characters")
	private String countryName;
	@NotBlank(message = "Shortcode should not be empty")
	@Size(min = 2, max = 5, message = "ShortCode must be between 2 to 5 characters long") 
	private String shortCode;
	private String status;
	private boolean deleteStatus;
}
