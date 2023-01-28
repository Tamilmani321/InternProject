package com.user.common;

import java.util.List;

import com.user.location.dtos.StateDto;

import lombok.Data;

@Data
public class StateDatas {

	
	private List <StateDto> state;
	private PaginationMeta pagination;
}
