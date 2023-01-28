package com.user.dao;

import org.springframework.http.ResponseEntity;

import com.user.dto.UserDto;

public interface UserDao {

	public ResponseEntity<?> getUser(Long us);

	public ResponseEntity<?> updateUser(UserDto use, Long us);

	public ResponseEntity<?> deleteUser(Long us);

	public ResponseEntity<?> links(Long id);
}
