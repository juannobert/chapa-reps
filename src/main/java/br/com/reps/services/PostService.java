package br.com.reps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.reps.dtos.responses.PostResponse;
import br.com.reps.mappers.PostMapper;
import br.com.reps.repositories.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private PostMapper mapper;
	
	public Page<PostResponse> buscarTodos(Pageable pageable){
		return postRepository.findAll(pageable)
				.map(mapper::toResponse);
	}
	
	
}
