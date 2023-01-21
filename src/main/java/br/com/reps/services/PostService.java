package br.com.reps.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.reps.dtos.requests.PostRequest;
import br.com.reps.dtos.requests.SupportRequest;
import br.com.reps.dtos.responses.PostResponse;
import br.com.reps.dtos.responses.SupportResponse;
import br.com.reps.entities.Post;
import br.com.reps.entities.enums.PostType;
import br.com.reps.mappers.PostMapper;
import br.com.reps.repositories.PostRepository;
import jakarta.persistence.EntityNotFoundException;

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
		return repository.findAllOrderTransparency(pageable)
				.map(mapper::toResponse);
	
	}
	
	public Page<SupportResponse> findAllSupports(Pageable pageable){
		return repository.findAllSupports(pageable)
				.map(mapper::toSupportResponse);
	}
	
	public Post insertNotice(PostRequest request) {
		Post entity = mapper.toModel(request);
		entity.setDate(LocalDate.now());
		return repository.save(entity);
	}
	
	public Post insertSupport(SupportRequest request) {
		Post entity = mapper.toModel(request);
		entity.setDate(LocalDate.now());
		entity.setPostType(PostType.SUPPORT);	
		return repository.save(entity);
	}
	
	
	
	public PostResponse findById(Long id) {
		return repository.findById(id)
				.stream()
				.map(mapper::toResponse)
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada"));
	}
	
	
	
	
	
	
	
}
