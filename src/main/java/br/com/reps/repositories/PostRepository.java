package br.com.reps.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.reps.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	@Query("SELECT p FROM Post p WHERE p.postType = 'NOTICE' ORDER BY p.id DESC")
	Page<Post> findAllOrderNotice(Pageable pageable);
	
	@Query("SELECT p FROM Post p WHERE p.postType = 'TRANSPARENCY' ORDER BY p.id DESC")
	Page<Post> findAllOrderTransparencia(Pageable pageable);

}
