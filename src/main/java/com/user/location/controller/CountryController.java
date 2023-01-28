package com.user.location.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.location.dtos.CountryDto;
import com.user.location.entities.Countries;
import com.user.location.serviceImpls.CountryServiceImpls;



@RestController
@RequestMapping("/location")
public class CountryController {

	@Autowired
	private CountryServiceImpls countryService;

	@PostMapping("/country")
	public ResponseEntity<?> SaveCountry(@RequestBody @Valid CountryDto s) {
		System.out.println("Iam Controller");
		return countryService.SaveCountry(s);
	}
	@GetMapping("/getCountriesByPage")
	public ResponseEntity<?> getCountriesByPage(@SortDefault(sort="countryId",direction = Direction.ASC) Pageable pageable){// this is default sorting descending
		return countryService.getCountriesByPage(pageable); 
	}

	@GetMapping("/country")
	public ResponseEntity<?> viewWithoutDeletedCountries() {
		return countryService.viewWithoutDeletedCountries();

	}

	@PatchMapping("/country/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable long id, @RequestBody @Valid CountryDto s) {
		return countryService.updateStatus(id, s);
	}

	@GetMapping("/country/{id}")
	public ResponseEntity<?> viewUsingCountryId(@PathVariable long id) {
		return countryService.viewUsingCountryId(id);

	}

	@PutMapping("/country/{id}")
	public ResponseEntity<?> updateCountry(@PathVariable long id, @RequestBody @Valid Countries con) {
		return countryService.updateCountry(id, con);
	}

	@GetMapping("/country/list")
	public ResponseEntity<?> viewAllCountries() {
		return countryService.viewAllCountries();

	}

	@DeleteMapping("/country/{id}")
	public ResponseEntity<?> deleteCountry(@PathVariable long id) {
		return countryService.deleteCountry(id);
	}

}
