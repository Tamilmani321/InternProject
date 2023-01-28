package com.user.location.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.location.entities.States;

import lombok.Data;

@Data
public class DistrictDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private long districtId;

	@NotBlank(message = "Districtname should not be empty")

	@Size(min = 5, message = "Districtname must contain atleast 5 characters")
	private String districtName;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private long stateId;
	private String status;
	@NotBlank(message = "Shortcode should not be empty")
	@Size(min = 2, max = 5, message = "Shortcode must be between 2 to 5 characters long")
	private String shortCode;
	private boolean deleteStatus;
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private StateDto states;

}
