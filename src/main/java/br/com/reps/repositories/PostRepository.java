package br.com.reps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.reps.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
