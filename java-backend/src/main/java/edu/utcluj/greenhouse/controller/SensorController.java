package edu.utcluj.greenhouse.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.utcluj.greenhouse.dto.SensorDto;
import edu.utcluj.greenhouse.model.Sensor;
import edu.utcluj.greenhouse.service.ISensorService;

@RestController
@RequestMapping("/sensor")
public class SensorController {

	@Autowired
	private ISensorService sensorService;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody Sensor sensor) {
		return sensorService.save(sensor) ? new ResponseEntity<>(HttpStatus.CREATED)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping
	public List<SensorDto> findAll(
			@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date1,
			@RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date2) {
		return this.sensorService.findAll(date1, date2);
	}

	/**
	 * 
	 * @param date1 (yyyy-mm-dd format in URI parameters)
	 * @param date2 (yyyy-mm-dd format in URI parameters)
	 * @return average temperature
	 */
	@GetMapping("/temperature-average")
	public ResponseEntity<?> getAverageTemperature(
			@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date1,
			@RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date2) {
		return new ResponseEntity<>(this.sensorService.getAverageTemperature(date1, date2), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param date1 (yyyy-mm-dd format in URI parameters)
	 * @param date2 (yyyy-mm-dd format in URI parameters)
	 * @return average temperature
	 */
	@GetMapping("/humidity-average")
	public ResponseEntity<?> getAverageHumidity(
			@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date1,
			@RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date2) {
		return new ResponseEntity<>(this.sensorService.getAverageHumidity(date1, date2), HttpStatus.OK);
	}

}
