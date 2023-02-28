package br.com.reps.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.reps.dtos.requests.AnswerRequest;
import br.com.reps.dtos.requests.PostRequest;
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
		entity.setDate(new Date());
		
		User user = securityUtils.getUsuarioLogado();
		entity.setAuthor(user);
		return repository.save(entity);
	}
	
	public Post insertSupport(PostRequest request) {
		request.setPostType(PostType.SUPPORT);
		Post entity = mapper.toModel(request);
		entity.setDate(new Date());

		User user = securityUtils.getUsuarioLogado();
		entity.setAuthor(user);
		return repository.save(entity);
	}


	public Post addComents(Long id,AnswerRequest request) {
		Post entity = repository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado"));
		
		Post answer = mapper.toModel(request);
		answer.setDate(new Date());
		User user = securityUtils.getUsuarioLogado();
		answer.setAuthor(user);
		answer = repository.save(answer);
		
		entity.getAnswers().add(answer);
		
		return repository.save(entity);		
	}
	
	
	public void delete(Long id) {
		Post model = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada"));
		if(model.getPostType() == null) {
			repository.deleteAnswerer(id);
		}
		else repository.deleteById(id);
	}
	
	public Post alter(Long id,PostRequest request) {
		Post model = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada"));
		updatePost(model,request);
		return repository.save(model);
	}
	
	private void updatePost(Post model, PostRequest postRequest) {
		model.setText(postRequest.getText());
		model.setTitle(postRequest.getTitle());
		
		if(postRequest.getPostType() != null)
			model.setPostType(postRequest.getPostType());
		
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
	
	public boolean verifyPost(Long postId) {
		PostResponse response = findById(postId);
		return securityUtils.getUsuarioLogado().getId() == response.getAuthor().getId();
		
	}
	
	

	
}
