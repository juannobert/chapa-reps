package br.com.reps.services;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import br.com.reps.dtos.requests.ForgetPasswordRequest;
import br.com.reps.dtos.requests.IUserRequest;
import br.com.reps.dtos.requests.UserAdminRequest;
import br.com.reps.dtos.requests.UserAlterResquest;
import br.com.reps.dtos.requests.UserDefaultRequest;
import br.com.reps.dtos.requests.UserRequest;
import br.com.reps.dtos.responses.AlterPasswordRequest;
import br.com.reps.dtos.responses.EmailParams;
import br.com.reps.dtos.responses.UserResponse;
import br.com.reps.entities.User;
import br.com.reps.entities.enums.UserRole;
import br.com.reps.mappers.UserMapper;
import br.com.reps.repositories.UserRepository;
import br.com.reps.services.exceptions.EntityNotFoundException;
import br.com.reps.services.exceptions.UserAlreadyExistsException;
import br.com.reps.services.exceptions.ValidationException;
import br.com.reps.utils.SecurityUtils;

@Service
public class UserService{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SecurityUtils securityUtils;
	
	
	public User insert(UserRequest request) {
		boolean isAdmin = request instanceof UserAdminRequest ? true : false;
		
		validarConfirmacaoDeSenha(request);
		User entity = isAdmin ? mapper.toAdminModel((UserAdminRequest)request) 
				: mapper.toDefaultModel((UserDefaultRequest)request);
		validateEmail(entity);
		UserRole role = isAdmin ? UserRole.GREMISTA  : UserRole.ALUNO;
		entity.setRole(role);
		
		String password = passwordEncoder.encode(entity.getPassword());
		entity.setPassword(password);
		return repository.save(entity);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	
	public User alterPassword(AlterPasswordRequest request,String email) {
		validarConfirmacaoDeSenha(request);
		User entity = repository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
		String password = passwordEncoder.encode(request.getPassword());
		entity.setPassword(password);
		
		return repository.save(entity);
		
		
	}
	
	public Page<UserResponse> findAll(Pageable pageable,UserRole role) {
		return repository.findAll(pageable,role)
				.map(mapper::toResponse);
	}
	
	public User alterUser(UserAlterResquest request) {
		User entity = mapper.alterUser(request, securityUtils.getUsuarioLogado());
		repository.save(entity);
		securityUtils.updateAuthenticatedUser(entity);
		return entity;
	}
	
	
	public UserResponse getAuthenticatedUser() {
		return mapper.toResponse(securityUtils.getUsuarioLogado());
				
	}
	
	
	public String recoveryCode(ForgetPasswordRequest request) {
		String email = request.getEmail();
		if(repository.findByEmail(email).isEmpty())
			return null;
		
		String code = generateCode();
		Map<String,Object> props = createProps(code);
		EmailParams emailParams = createEmailParams(email, props);
		
		emailService.sendEmail(emailParams);
		
		return code;
		
	}
	
	

	
	private Map<String,Object> createProps(String code){
		HashMap<String,Object> props = new HashMap<String,Object>();
		props.put("code", code);
		return props;
	}
	
	private EmailParams createEmailParams(String email,Map<String,Object> props) {
		EmailParams emailParams = new EmailParams();
		emailParams.setTo(email);
		emailParams.setSubject("Código de alteração de senha");
		emailParams.setTemplate("email/forget-password");
		emailParams.setProps(props);
		return emailParams;
		
	}
	
	private String generateCode() {
		Random r = new Random();
		String code = "";
		for(int i = 0; i < 7;i++) {
			code += String.valueOf(r.nextInt(9));
		}
		return code;
	}
	
	private void validateEmail(User user) {
		if(repository.isEmailJaCdastrado(user)) {
			String msg = "Email já foi cadastrado";
			FieldError fieldError = new FieldError(user.getClass().getName(), "email", user.getEmail(), false, null, null, msg);
			throw new UserAlreadyExistsException(fieldError, msg);
		}
	}
	
	private void validarConfirmacaoDeSenha(IUserRequest obj) {
		String senha = obj.getPassword();
		String confirmacaoSenha = obj.getPasswordConfirmation();
		
		if (!senha.equals(confirmacaoSenha)) {
			String msg = "As senhas não conferem";
			FieldError fieldError = new FieldError(obj.getClass().getName(), "passwordConfirmation",
					obj.getPasswordConfirmation(), false, null, null, msg);
			throw new ValidationException(fieldError,msg);
		}
	}
	
	
	
		
}
