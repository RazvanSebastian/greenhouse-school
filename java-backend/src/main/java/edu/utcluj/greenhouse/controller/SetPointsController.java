package edu.utcluj.greenhouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.utcluj.greenhouse.exception.OutOfBoundsException;
import edu.utcluj.greenhouse.model.SetPoints;
import edu.utcluj.greenhouse.service.ISetPointsService;

@RestController
@RequestMapping("/set-points")
public class SetPointsController {

	@Autowired
	private ISetPointsService setPointsService;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody SetPoints setPoints) {
		try {
			setPointsService.save(setPoints);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (OutOfBoundsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(setPointsService.findAll(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> findLatest() {
		return new ResponseEntity<>(setPointsService.find(), HttpStatus.OK);
	}

}
