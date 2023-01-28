package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.Attachments;

public interface ImageRepository extends JpaRepository<Attachments, Long> {

}
