package com.user.location.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.user.auditables.Auditable;

import lombok.Data;

@Entity
@Data
public class Districts extends Auditable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long districtId;
	@Column(unique = true,length = 40)
	private String districtName;
	private boolean deleteStatus;
	@Column(unique = true,length = 6)
	private String shortCode;
	private String status;
	@ManyToOne
	@JoinColumn(name = "stateId")
	private States states;

}
