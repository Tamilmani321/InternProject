package com.user.dto;

import org.springframework.stereotype.Component;

import com.user.entity.Roles;

import lombok.Data;

@Data
@Component
public class TokenValues {
	private int id;
	private String name;
	private String email;
	private Roles roles;
}
