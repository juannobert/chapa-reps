package br.com.reps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.reps.dtos.requests.UserAdminRequest;
import br.com.reps.dtos.requests.UserRequest;
import br.com.reps.entities.User;
import br.com.reps.entities.enums.UserRole;
import br.com.reps.mappers.UserMapper;
import br.com.reps.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	
	public User insert(UserAdminRequest request,boolean isAdmin) {
		User entity = mapper.toAdminModel(request);
		UserRole role = isAdmin ? UserRole.GREMISTA  : UserRole.ALUNO;
		entity.setRole(role);
		return repository.save(entity);
	}
}
