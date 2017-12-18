package edu.utcluj.greenhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.utcluj.greenhouse.model.SetPoints;

@Repository
public interface ISetPointsRepository extends JpaRepository<SetPoints, Long>{
	
	@Query("select sp from SetPoints sp where sp.temperatureSetPoint =:t and sp.humiditySetPoint =:h")
	SetPoints find(@Param("t") int t, @Param("h") int h);
	
	@Query("select sp from SetPoints sp where sp.date = (select MAX(date) from SetPoints)")
	SetPoints findLates();
	
}
