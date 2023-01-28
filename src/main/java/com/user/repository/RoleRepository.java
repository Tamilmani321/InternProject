package com.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.Roles;
import com.user.entity.User;

public interface RoleRepository extends JpaRepository<Roles, Long>{

	
	Page<Roles> findAllByOrderByIdAsc(Pageable pageable);
	
	Roles findByname(Object object);
}
