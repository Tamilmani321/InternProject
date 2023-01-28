package com.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.user.auditables.Auditable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Roles extends Auditable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true,length = 20)
	private String name;
	private boolean deleteStatus;

}
