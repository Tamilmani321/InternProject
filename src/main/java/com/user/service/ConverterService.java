package com.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.user.dto.RoleDto;
import com.user.dto.UserDto;
import com.user.entity.Roles;
import com.user.entity.User;


@Component
public class ConverterService {

	@Autowired
	private ModelMapper modelMapper;
	
	public UserDto convertToUserDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}
	public User convertToUser(UserDto userDto) {
		return modelMapper.map(userDto, User.class);
	}
	public RoleDto convertToRoleDto(Roles role) {
		return modelMapper.map(role, RoleDto.class);
	}
	public Roles convertToRole(RoleDto roleDto) {
		return modelMapper.map(roleDto, Roles.class);
	}
}
