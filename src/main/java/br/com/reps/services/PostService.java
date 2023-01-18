package br.com.reps.services;

import java.time.LocalDate;

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
	
	public Page<PostResponse> buscarTodosAvisos(Pageable pageable){
		return repository.findAllOrderNotice(pageable)
				.map(mapper::toResponse);
	}
	
	public Page<PostResponse> buscarTodosTransparencia(Pageable pageable){
		return repository.findAllOrderTransparencia(pageable)
				.map(mapper::toResponse);
	}
	
	public Post inserir(PostRequest request) {
		Post entity = mapper.toModel(request);
		entity.setDate(LocalDate.now());
		return repository.save(entity);

	}
	
	
}
