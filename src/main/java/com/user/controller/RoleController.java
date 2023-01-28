package com.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.user.service.RoleService;
import com.user.dto.RoleDto;

@RestController
public class RoleController {

	@Autowired
	RoleService roleService;

	@PostMapping("/saveRole")
	public ResponseEntity<?> saveRole(@RequestBody @Valid RoleDto role) {
		return roleService.saveRole(role);
	}

	@GetMapping("/getRole/{id}")
	public ResponseEntity<?> getRole(@PathVariable Long id) {
		return roleService.getRole(id);
	}

	@GetMapping("/getRoleByPage")
	public ResponseEntity<?> getRoleByPage(@SortDefault(sort="id",direction = Direction.ASC) Pageable pageable){// this is default sorting descending
		return roleService.getRoleByPage(pageable);//dynamic sort it will allow default sort and user sort 
	}
	@PutMapping("/updateRole/{id}")
	public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody @Valid RoleDto emp) {
		return roleService.updateRole(emp, id);
	}
	@GetMapping("getRole/links/{id}")
	public ResponseEntity<?> links(@PathVariable Long id){
		return roleService.links(id);
	}

	@DeleteMapping("/deleteRole/{id}")
	public ResponseEntity<?> deleteRole(@PathVariable Long id) {
		return roleService.deleteRole(id);
	}
}
