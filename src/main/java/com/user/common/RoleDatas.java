package com.user.common;

import java.util.List;

import com.user.dto.RoleDto;


import lombok.Data;

@Data
public class RoleDatas {

	
	private List <RoleDto> roles;
	private PaginationMeta pagination;
}
