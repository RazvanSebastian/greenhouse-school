package edu.utcluj.greenhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.utcluj.greenhouse.model.Sensor;

@Repository
public interface ISensorRepository extends JpaRepository<Sensor, Long> {

}
