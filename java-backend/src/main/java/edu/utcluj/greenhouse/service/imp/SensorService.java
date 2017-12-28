package edu.utcluj.greenhouse.service.imp;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.utcluj.greenhouse.dto.SensorDto;
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
	public List<SensorDto> findAll(Date date1, Date date2) {
		return this.sensorRepository.findAll(date1, date2)
				.stream()
				.map(s -> new SensorDto(s.getTemperature(), s.getHumidity()))
				.collect(Collectors.toList());
	}

	@Override
	public double getAverageTemperature(Date date1, Date date2) {
		return this.sensorRepository.computeTemperatueAverage(date1, date2);
	}

	@Override
	public double getAverageHumidity(Date date1, Date date2) {
		return this.sensorRepository.computeHumidityAverage(date1, date2);
	}

}
