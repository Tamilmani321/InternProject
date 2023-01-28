package com.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Page<User> findAllByOrderByIdAsc(Pageable pageable);

	User findByNameAndPassword(String name, String password);

	User findByname(String name);
}
