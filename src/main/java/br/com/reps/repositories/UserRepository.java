package br.com.reps.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.reps.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
	
	default boolean isEmailJaCdastrado(User usuario) {
		if (usuario.getEmail() == null) {
			return false;
		}

		return findByEmail(usuario.getEmail())
				.map(usuarioEncontrado -> !usuarioEncontrado.getId().equals(usuario.getId()))
				.orElse(false);
	}
	
	
}
