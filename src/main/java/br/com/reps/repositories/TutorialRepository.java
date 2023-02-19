package br.com.reps.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.reps.entities.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long>{
	
	@Query("SELECT t  FROM Tutorial t ORDER BY t.id DESC")
	Page<Tutorial> findAll(Pageable pageable);
}
