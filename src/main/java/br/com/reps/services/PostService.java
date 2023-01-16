package br.com.reps.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<PostResponse> buscarTodos(){
		return postRepository.findAll()
				.stream()
				.map(mapper::toResponse)
				.toList();
	}
	
	
}
