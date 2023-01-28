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
import com.user.location.dtos.StateDto;
import com.user.location.entities.States;
import com.user.location.interfaces.StateService;
import com.user.location.serviceImpls.StateServiceImpl;

@RestController
@RequestMapping("/location")
public class StateController {

	@Autowired
	private StateServiceImpl stateService;
	
	@PostMapping("/state")
	public ResponseEntity<?> SaveState(@RequestBody @Valid StateDto s) {
		return stateService.SaveState(s);
	}
	@GetMapping("/getStatesByPage")
	public ResponseEntity<?> getStatesByPage(@SortDefault(sort="stateId",direction = Direction.ASC) Pageable pageable){// this is default sorting descending
		return stateService.getStatesByPage(pageable); 
	}

	@GetMapping("/state")
	public ResponseEntity<?> getAllStates() {
		return stateService.getAllStates();

	}

	@GetMapping("/state/{sid}")
	public ResponseEntity<?> getStatesByStateId(@PathVariable long sid) {
		return stateService.getStatesByStateId(sid);
	}

	@GetMapping("/state/list/{cid}")
	public ResponseEntity<?> getStatesByCountryid(@PathVariable long cid) {
		return stateService.getStatesByCountryid(cid);

	}

	@PatchMapping("/state/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable long id, @RequestBody StateDto st) {
		return stateService.updateStatus(id, st);
	}

	
	
	@PutMapping("/state/{id}")
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid StateDto st) {
		return stateService.updateState(id, st);
	}
	 
	 

	@DeleteMapping("/state/{cid}")
	public ResponseEntity<?> deleteState(@PathVariable long cid) {
		return stateService.deleteState(cid);
	}

}
