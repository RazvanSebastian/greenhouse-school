package edu.utcluj.greenhouse.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.utcluj.greenhouse.model.Sensor;
import edu.utcluj.greenhouse.repository.ISensorRepository;
import edu.utcluj.greenhouse.service.ISensorService;

@Component
public class SensorService implements ISensorService {
	
	@Autowired
	private ISensorRepository sensorRepository;

	@Override
	public boolean save(Sensor sensor) {
		sensor.setDate(new Date(System.currentTimeMillis()));
		this.sensorRepository.save(sensor);
		return true;		
	}

	@Override
	public List<Sensor> findAll() {
		return this.sensorRepository.findAll();
	}
	
}
