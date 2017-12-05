package edu.utcluj.greenhouse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.utcluj.greenhouse.model.Sensor;

@Service
public interface ISensorService {
	
	public boolean save(Sensor sensor);
	
	public List<Sensor> findAll();

}
