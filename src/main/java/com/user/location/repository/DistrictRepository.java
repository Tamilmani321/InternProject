package com.user.location.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.user.location.entities.Districts;
import com.user.location.entities.States;


@Repository

public interface DistrictRepository extends JpaRepository<Districts, Long>{
	
	
	List<Districts> findByStates(States id);
	List<Districts> findBydeleteStatus(boolean s);
	Districts findBydistrictName(Object object);

}
