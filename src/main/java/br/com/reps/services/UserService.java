package br.com.reps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import br.com.reps.dtos.requests.UserAdminRequest;
import br.com.reps.dtos.requests.UserRequest;
import br.com.reps.entities.User;
import br.com.reps.entities.enums.UserRole;
import br.com.reps.mappers.UserMapper;
import br.com.reps.repositories.UserRepository;
import br.com.reps.services.exceptions.UserAlreadyExistsException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	
	public User insert(UserAdminRequest request,boolean isAdmin) {
		User entity = mapper.toAdminModel(request);
		validateEmail(entity);
		UserRole role = isAdmin ? UserRole.GREMISTA  : UserRole.ALUNO;
		entity.setRole(role);
		return repository.save(entity);
	}
	
	private void validateEmail(User user) {
		if(repository.isEmailJaCdastrado(user)) {
			String msg = "Email já foi cadastrado";
			FieldError fieldError = new FieldError(user.getClass().getName(), "email", user.getEmail(), false, null, null, msg);
			throw new UserAlreadyExistsException(fieldError, msg);
		}
	}
	
		
}
