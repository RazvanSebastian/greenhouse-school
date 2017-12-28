package edu.utcluj.greenhouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.utcluj.greenhouse.dto.User;
import edu.utcluj.greenhouse.exception.UserNotFoundException;
import edu.utcluj.greenhouse.service.ILoginService;

@RestController
public class LoginController {

	@Autowired
	private ILoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<?> getBasicAuthorizationToken(@RequestBody User user) {
		try {
			return new ResponseEntity<>(loginService.generateBasicAuthorizationtoken(user), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
}
