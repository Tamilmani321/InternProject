package com.user.location.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.user.location.dtos.DistrictDto;


public interface DistrictService {

	public ResponseEntity<?> SaveDistrict(DistrictDto st);

	public ResponseEntity<?> getAllDistricts();

	public ResponseEntity<?> getDistrictsByDistrictId(long id);

	public ResponseEntity<?> getDistrictsBySid(long id);

	public ResponseEntity<?> deleteDistrict(long id);

	public ResponseEntity<?> updateStatus(long id, DistrictDto d);

	public ResponseEntity<?> updateDistrict(long id, DistrictDto d);

}
