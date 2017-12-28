package edu.utcluj.greenhouse.service.imp;

import java.util.Base64;

import org.springframework.stereotype.Component;

import edu.utcluj.greenhouse.dto.User;
import edu.utcluj.greenhouse.exception.UserNotFoundException;
import edu.utcluj.greenhouse.service.ILoginService;

@Component
public class LoginService implements ILoginService {
	@Override
	public String generateBasicAuthorizationtoken(User user) throws UserNotFoundException {
		if (!user.getUser().equals("greenhouse") || !user.getPassword().equals("strongpassword"))
			throw new UserNotFoundException();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(user.getUser()).append(":").append(user.getPassword());

		return Base64.getEncoder().encodeToString(stringBuilder.toString().getBytes());
	}

}
