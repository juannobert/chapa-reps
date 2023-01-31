package br.com.reps.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.reps.dtos.requests.AnswerRequest;
import br.com.reps.dtos.requests.PostRequest;
import br.com.reps.dtos.requests.SupportRequest;
import br.com.reps.dtos.responses.PostResponse;
import br.com.reps.entities.Post;
import br.com.reps.entities.User;
import br.com.reps.entities.enums.PostType;
import br.com.reps.mappers.PostMapper;
import br.com.reps.repositories.PostRepository;
import br.com.reps.services.exceptions.EntityNotFoundException;
import br.com.reps.utils.SecurityUtils;

@Service
public class PostService {

	@Autowired
	private PostRepository repository;
	
	@Autowired
	private PostMapper mapper;
	
	@Autowired
	private SecurityUtils securityUtils;
	
	public Page<PostResponse> findAllPosts(Pageable pageable,PostType postType){
		return repository.findAll(pageable,postType)
				.map(mapper::toResponse);
	}
	
	public Post insertNotice(PostRequest request) {
		Post entity = mapper.toModel(request);
		entity.setDate(LocalDate.now());
		
		User user = securityUtils.getUsuarioLogado();
		entity.setAuthor(user);
		return repository.save(entity);
	}
	
	public Post insertSupport(SupportRequest request) {
		Post entity = mapper.toModel(request);
		entity.setDate(LocalDate.now());
		entity.setPostType(PostType.SUPPORT);	

		User user = securityUtils.getUsuarioLogado();
		entity.setAuthor(user);
		return repository.save(entity);
	}


	public Post addComents(Long id,AnswerRequest request) {
		Post entity = repository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado"));
		
		Post answer = mapper.toModel(request);
		answer.setDate(LocalDate.now());
		User user = securityUtils.getUsuarioLogado();
		answer.setAuthor(user);
		answer = repository.save(answer);
		
		entity.getAnswers().add(answer);
		
		return repository.save(entity);		
	}
	
	public PostResponse findById(Long id) {
		return repository.findById(id)
				.stream()
				.map(mapper::toResponse)
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada"));
	}
	
	public boolean isSupportbyId(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada"))
				.isSupport();
	}
	

	
}
