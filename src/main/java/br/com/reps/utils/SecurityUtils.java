package br.com.reps.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.reps.entities.User;
import br.com.reps.entities.enums.UserRole;
import br.com.reps.repositories.UserRepository;
import br.com.reps.services.exceptions.EntityNotFoundException;

@Component
public class SecurityUtils {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public String getEmailLogado() {
		return getAuthentication().getName();
	}
	
	public User getUsuarioLogado() {
		String email = getEmailLogado();
		String msg = "Usuário com email %s não encontrado";
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException(msg));
	}
	
	public void updateAuthenticatedUser(User updatedUser) {
	    Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

	    UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
	        updatedUser.getUsername(), currentAuth.getCredentials(), currentAuth.getAuthorities());

	    SecurityContextHolder.getContext().setAuthentication(newAuth);
	    
	}
	
	public boolean isGremista() {
		return getUsuarioLogado().getRole().equals(UserRole.GREMISTA);
	}
}
