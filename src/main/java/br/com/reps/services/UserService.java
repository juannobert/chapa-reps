package br.com.reps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import br.com.reps.dtos.requests.UserAdminRequest;
import br.com.reps.dtos.requests.UserRequest;
import br.com.reps.entities.User;
import br.com.reps.entities.enums.UserRole;
import br.com.reps.mappers.UserMapper;
import br.com.reps.repositories.UserRepository;
import br.com.reps.services.exceptions.UserAlreadyExistsException;
import br.com.reps.services.exceptions.ValidationException;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User insert(UserAdminRequest request,boolean isAdmin) {
		validarConfirmacaoDeSenha(request);
		User entity = mapper.toAdminModel(request);
		validateEmail(entity);
		UserRole role = isAdmin ? UserRole.GREMISTA  : UserRole.ALUNO;
		entity.setRole(role);
		
		System.out.println(entity.getPassword());
		String password = passwordEncoder.encode(entity.getPassword());
		entity.setPassword(password);
		return repository.save(entity);
	}
	
	private void validateEmail(User user) {
		if(repository.isEmailJaCdastrado(user)) {
			String msg = "Email já foi cadastrado";
			FieldError fieldError = new FieldError(user.getClass().getName(), "email", user.getEmail(), false, null, null, msg);
			throw new UserAlreadyExistsException(fieldError, msg);
		}
	}
	
	private void validarConfirmacaoDeSenha(UserRequest obj) {
		String senha = obj.getPassword();
		String confirmacaoSenha = obj.getPasswordConfirmation();
		
		if (!senha.equals(confirmacaoSenha)) {
			String msg = "As senhas não conferem";
			FieldError fieldError = new FieldError(obj.getClass().getName(), "passwordConfirmation",
					obj.getPasswordConfirmation(), false, null, null, msg);
			throw new ValidationException(fieldError,msg);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		return repository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
	
		
}
