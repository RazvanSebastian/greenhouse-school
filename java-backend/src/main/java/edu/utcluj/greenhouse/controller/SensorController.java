package edu.utcluj.greenhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public List<Sensor> findAll() {
		return this.sensorService.findAll();
	}

}
