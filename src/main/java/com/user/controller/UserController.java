package com.user.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;


import com.user.dto.UserDto;

import com.user.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	
	
	
	@GetMapping("/getUsersByPage")
	public ResponseEntity<?> getUserByPage(@SortDefault(sort="id",direction = Direction.ASC) Pageable pageable){// this is default sorting descending
		return userService.getUserByPage(pageable); 
	}
	@GetMapping("getUser/links/{id}")
	public ResponseEntity<?> menu(@PathVariable Long id){
		return userService.links(id);
	}
	@GetMapping("/getUser/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}

	@PutMapping("/updateUser/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserDto emp) {
		return userService.updateUser(emp, id);
	}

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		return userService.deleteUser(id);
	}

}
