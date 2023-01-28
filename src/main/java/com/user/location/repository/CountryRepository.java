package com.user.location.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.location.entities.Countries;
@Repository
public interface CountryRepository extends JpaRepository<Countries, Long>{

	List<Countries> findBydeleteStatus(boolean s);
	Countries findBycountryName(Object object);
	
}
