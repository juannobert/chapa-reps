package br.com.reps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.reps.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
