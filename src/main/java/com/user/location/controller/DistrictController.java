package com.user.location.controller;

import java.util.List;

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

import com.user.location.dtos.DistrictDto;
import com.user.location.entities.Districts;
import com.user.location.serviceImpls.DistrictServiceImpl;

@RestController
@RequestMapping("/location")
public class DistrictController {

	@Autowired
	private DistrictServiceImpl districtService;

	@PostMapping("/district")
	public ResponseEntity<?> SaveDistrict(@RequestBody @Valid DistrictDto s) {
		return districtService.SaveDistrict(s);
	}

	@GetMapping("/district")
	public ResponseEntity<?> getAllDistricts() {
		return districtService.getAllDistricts();

	}
	@GetMapping("/getDistrictsByPage")
	public ResponseEntity<?> getDistrictsByPage(@SortDefault(sort="districtId",direction = Direction.ASC) Pageable pageable){// this is default sorting descending
		return districtService.getDistrictsByPage(pageable); 
	}

	@GetMapping("/district/{id}")
	public ResponseEntity<?> usingDid(@PathVariable long id) {
		return districtService.getDistrictsByDistrictId(id);
	}

	@DeleteMapping("/district/{id}")
	public ResponseEntity<?> deleteDistrict(@PathVariable long id) {
		return districtService.deleteDistrict(id);
	}

	@GetMapping("/district/list/{id}")
	public ResponseEntity<?> getDistrictsBySid(@PathVariable long id) {
		return districtService.getDistrictsBySid(id);
	}

	@PutMapping("/district/{id}")
	public ResponseEntity<?> updateDistrict(@PathVariable long id, @RequestBody @Valid DistrictDto di) {
		return districtService.updateDistrict(id, di);
	}

	@PatchMapping("/district/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable long id, @RequestBody @Valid DistrictDto di) {
		return districtService.updateStatus(id, di);
	}

}
