package edu.utcluj.greenhouse.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.utcluj.greenhouse.model.Sensor;

@Repository
public interface ISensorRepository extends JpaRepository<Sensor, Long> {

	@Query("SELECT sensor FROM Sensor sensor WHERE sensor.date BETWEEN :date1 AND :date2")
	List<Sensor> findAll(@Param("date1") Date date1, @Param("date2") Date date2);

	@Query("SELECT AVG(sensor.temperature) FROM Sensor sensor WHERE sensor.date BETWEEN :date1 AND :date2")
	double computeTemperatueAverage(@Param("date1") Date date1, @Param("date2") Date date2);

	@Query("SELECT AVG(sensor.humidity) FROM Sensor sensor WHERE sensor.date BETWEEN :date1 AND :date2")
	double computeHumidityAverage(@Param("date1") Date date1, @Param("date2") Date date2);
}
