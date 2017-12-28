package edu.utcluj.greenhouse.service;

import org.springframework.stereotype.Service;

import edu.utcluj.greenhouse.dto.User;
import edu.utcluj.greenhouse.exception.UserNotFoundException;

@Service
public interface ILoginService {

	/**
	 * If the user and password are matching, the method will generate a token
	 * encoded in base64 to use it to get authorization to do requests
	 * 
	 * @param user
	 * @return base64 string token
	 * @throws UserNotFoundException
	 */
	public String generateBasicAuthorizationtoken(User user) throws UserNotFoundException;
}
