package com.user.location.dtos;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.location.entities.Countries;

import lombok.Data;
@Data
public class StateDto {
	
	@JsonProperty(access=JsonProperty.Access.READ_ONLY)
	private long stateId;
	@NotBlank(message = "Statename should not be empty")
	@Size(min=5, message = "Statename must contain atleast 5 characters")
	private String stateName;
	private long countryId;
	private String status;
	@NotBlank(message = "Shortcode should not be empty")
	@Size(min = 2, max = 5, message = "ShortCode must be between 2 to 5 characters long") 
	private String shortCode;
	private boolean deleteStatus;
	@JsonProperty(access=JsonProperty.Access.READ_ONLY)
	private CountryDto country;
}
