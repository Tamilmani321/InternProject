package com.user.location.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.user.auditables.Auditable;

import lombok.Data;

@Entity
@Data
public class Countries extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long countryId;
	@Column(unique = true,length = 40)
	private String countryName;
	@Column(unique = true,length = 6)
	private String shortCode;
	private String status;
	private boolean deleteStatus;

}
