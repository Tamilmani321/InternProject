package com.user.location.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.user.location.entities.Countries;
import com.user.location.entities.States;
@Repository

public interface StateRepository  extends JpaRepository<States, Long>{
	
	List<States> findByCountry(Countries c);
	List<States> findBydeleteStatus(boolean s);
	States findBystateName(Object object);
		
	

}
