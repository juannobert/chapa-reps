package br.com.reps.mappers;

import org.springframework.stereotype.Component;

import br.com.reps.dtos.requests.UserAdminRequest;
import br.com.reps.dtos.requests.UserAlterResquest;
import br.com.reps.dtos.requests.UserDefaultRequest;
import br.com.reps.dtos.requests.UserRequest;
import br.com.reps.dtos.responses.UserResponse;
import br.com.reps.entities.User;

@Component
public class UserMapper {
	

	
	public User toAdminModel(UserAdminRequest request) {
		User user = comonsUser(request);
		user.setOfficie(request.getOfficie());
		return user;
	}
	
	public UserResponse toResponse(User user) {
		UserResponse response = new UserResponse();
		response.setId(user.getId());
		response.setEmail(user.getEmail());
		response.setName(user.getName());
		response.setFunction(user.getFunction());
		response.setOfficie(user.getOfficie());
		return response;
	}
	
	public User toDefaultModel(UserDefaultRequest request) {
		User user = comonsUser(request);
		user.setFunction(request.getFunction());
		return user;
	}
	
	public User alterUser(UserAlterResquest request,User user) {
		user.setEmail(request.getEmail());
		user.setName(request.getName());
		if(request.getOfficie() != null) user.setOfficie(request.getOfficie());
		else user.setFunction(request.getFunction());
		return user;
		
	}

	
	private User comonsUser(UserRequest request) {
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		return user;
	}
}
