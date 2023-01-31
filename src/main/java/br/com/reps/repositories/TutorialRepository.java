package br.com.reps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.reps.entities.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long>{
	
	
}
