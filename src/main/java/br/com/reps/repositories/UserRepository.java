package br.com.reps.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.reps.entities.User;
import br.com.reps.entities.enums.UserRole;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.role = :userRole")
	Page<User>  findAll(Pageable pageable,UserRole userRole);
	
	
	
	default boolean isEmailJaCdastrado(User usuario) {
		if (usuario.getEmail() == null) {
			return false;
		}

		return findByEmail(usuario.getEmail())
				.map(usuarioEncontrado -> !usuarioEncontrado.getId().equals(usuario.getId()))
				.orElse(false);
	}
	
	
}
