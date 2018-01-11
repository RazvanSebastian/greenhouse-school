package edu.utcluj.greenhouse.service;

import org.springframework.stereotype.Service;

import edu.utcluj.greenhouse.dto.AuthToken;
import edu.utcluj.greenhouse.dto.User;
import edu.utcluj.greenhouse.exception.UserNotFoundException;

@Service
public interface ILoginService {

	/**
	 * If the user and password are matching, the method will generate a token
	 * encoded in base64 to use it to get authorization to do requests
	 * 
	 * @param user
	 * @return AuthToken object with token parameter containing base64 string token
	 * @throws UserNotFoundException
	 */
	public AuthToken generateBasicAuthorizationtoken(User user) throws UserNotFoundException;
}
