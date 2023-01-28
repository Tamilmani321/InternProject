package com.user.common;

import java.util.List;

import com.user.dto.UserDto;

import lombok.Data;

@Data
public class UserDatas {

	
	private List <UserDto> user;
	private PaginationMeta pagination;
}
