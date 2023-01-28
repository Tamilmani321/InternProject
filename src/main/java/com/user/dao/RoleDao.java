package com.user.dao;

import org.springframework.http.ResponseEntity;

import com.user.dto.RoleDto;
import com.user.entity.Roles;
import com.user.exception.RoleNotFoundException;

public interface RoleDao {

	public ResponseEntity<?> saveRole(RoleDto roleDto) ;

	public ResponseEntity<?> getRole(Long a) ;

	public ResponseEntity<?> updateRole(RoleDto use, Long us) ;

	public ResponseEntity<?> deleteRole(Long us) ;
	
	public ResponseEntity<?> links(Long id) ;
}
