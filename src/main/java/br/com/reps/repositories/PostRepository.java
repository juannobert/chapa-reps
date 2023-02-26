package br.com.reps.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.reps.entities.Post;
import br.com.reps.entities.enums.PostType;
import jakarta.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	@Query("SELECT p FROM Post p WHERE p.postType = :postType ORDER BY p.id DESC")
	Page<Post> findAll(Pageable pageable,PostType postType);
	
	@Transactional
	@Query(nativeQuery = true,value = "DELETE FROM tb_post_answers pa WHERE pa.answers_id = :id")
	@Modifying
	void deleteAnswerer(Long id);

}
