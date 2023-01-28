package com.user.location.interfaces;

import org.springframework.http.ResponseEntity;
import com.user.location.dtos.StateDto;
import com.user.exception.CountryNotFoundException;
import com.user.exception.StateNotFoundException;

public interface StateService {

	public ResponseEntity<?> SaveState(StateDto st);

	public ResponseEntity<?> getAllStates();

	public ResponseEntity<?> getStatesByStateId(long id);

	public ResponseEntity<?> getStatesByCountryid(long id);

	public ResponseEntity<?> deleteState(long id);

	public ResponseEntity<?> updateStatus(long id, StateDto d);

	public ResponseEntity<?> updateState(long id, StateDto d);

}
