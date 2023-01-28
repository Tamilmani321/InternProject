package com.user.common;

import java.util.List;

import com.user.location.dtos.CountryDto;

import lombok.Data;

@Data
public class CountryDatas {

	
	private List <CountryDto> country;
	private PaginationMeta pagination;
}
