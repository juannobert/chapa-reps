package br.com.reps.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.reps.entities.User;
import br.com.reps.repositories.UserRepository;
import br.com.reps.services.exceptions.EntityNotFoundException;

@Component
public class SecurityUtils {

	@Autowired
	private UserRepository userRepository;
	
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
}
