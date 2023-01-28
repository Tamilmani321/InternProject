package com.user.location.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.user.location.dtos.CountryDto;
import com.user.location.entities.Countries;
import com.user.exception.CountryNotFoundException;



public interface CountryService {

	public ResponseEntity<?> SaveCountry(CountryDto s);

	public ResponseEntity<?> viewWithoutDeletedCountries();

	public ResponseEntity<?> viewUsingCountryId(long id) ;

	public ResponseEntity<?> updateCountry(long id, Countries con) ;

	public ResponseEntity<?> viewAllCountries();

	public ResponseEntity<?> deleteCountry(long id) ;

	public ResponseEntity<?> updateStatus(long id, CountryDto s) throws CountryNotFoundException;

}
