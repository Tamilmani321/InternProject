package com.user.common;

import java.util.List;

import com.user.location.dtos.DistrictDto;

import lombok.Data;

@Data
public class DistrictDatas {

	
	private List <DistrictDto> district;
	private PaginationMeta pagination;
}
