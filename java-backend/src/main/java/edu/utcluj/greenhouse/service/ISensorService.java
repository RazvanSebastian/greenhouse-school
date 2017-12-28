package edu.utcluj.greenhouse.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.utcluj.greenhouse.dto.SensorDto;
import edu.utcluj.greenhouse.model.Sensor;

@Service
public interface ISensorService {
	
	public boolean save(Sensor sensor);
	
	public List<SensorDto> findAll(Date date1, Date date2);
	
	public double getAverageHumidity(Date date1, Date date2);

	public double getAverageTemperature(Date date1, Date date2);

}
