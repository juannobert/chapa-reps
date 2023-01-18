package br.com.reps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.reps.dtos.requests.PostRequest;
import br.com.reps.dtos.responses.PostResponse;
import br.com.reps.entities.Post;
import br.com.reps.mappers.PostMapper;
import br.com.reps.repositories.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository repository;
	
	@Autowired
	private PostMapper mapper;
	
	public Page<PostResponse> buscarTodos(Pageable pageable){
		return repository.findAll(pageable)
				.map(mapper::toResponse);
	}
	
	
	public Post inserir(PostRequest request) {
		Post entity = mapper.toModel(request);
		return repository.save(entity);

	}
	
	
}
