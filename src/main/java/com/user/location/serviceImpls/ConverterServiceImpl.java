package com.user.location.serviceImpls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.user.location.dtos.CountryDto;
import com.user.location.dtos.DistrictDto;
import com.user.location.dtos.StateDto;
import com.user.location.entities.Countries;
import com.user.location.entities.Districts;
import com.user.location.entities.States;


@Component
public class ConverterServiceImpl {

	@Autowired
	private ModelMapper modelMapper;
	
	public CountryDto convertToCountryDto(Countries countries) {
		return modelMapper.map(countries, CountryDto.class);
	}
	public Countries convertToCountries(CountryDto countryDto) {
		return modelMapper.map(countryDto, Countries.class);
	}
	public StateDto convertToStateDto(States states) {
		return modelMapper.map(states, StateDto.class);
	}
	public States convertToStates(StateDto stateDto) {
		return modelMapper.map(stateDto, States.class);
	}
	public DistrictDto convertToDistrictDto(Districts districts) {
		return modelMapper.map(districts, DistrictDto.class);
	}
	public Districts convertToDistricts(DistrictDto districtDto) {
		return modelMapper.map(districtDto, Districts.class);
	}
	
	
	
	
	
	
}
