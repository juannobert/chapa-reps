package br.com.reps.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.reps.dtos.requests.TutorialRequest;
import br.com.reps.dtos.responses.TutorialResponse;
import br.com.reps.entities.Tutorial;
import br.com.reps.entities.User;
import br.com.reps.mappers.TutorialMapper;
import br.com.reps.repositories.TutorialRepository;
import br.com.reps.services.exceptions.EntityNotFoundException;
import br.com.reps.utils.SecurityUtils;
import jakarta.transaction.Transactional;

@Service
public class TutorialService {

	@Autowired
	private TutorialRepository repository;
	
	@Autowired
	private TutorialMapper mapper;
	
	@Autowired
	private SecurityUtils securityUtils;
	
	public Page<TutorialResponse> findAll(Pageable pageable){
		return repository.findAll(pageable)
				.map(mapper::toResponse);
	}
	
	@Transactional
	public Tutorial insert(TutorialRequest request) {
		String embedLink = toEmbedLink(request.getUrl());
		Tutorial entity = mapper.toModel(request);
		User author = securityUtils.getUsuarioLogado(); 
		entity.setDate(new Date());
		entity.setAuthor(author);
		
		entity.setUrl(embedLink);
		
		return repository.save(entity);
	}
	
	public TutorialResponse findByid(Long id) {
		return repository.findById(id)
				.map(mapper::toResponse)
				.orElseThrow(() -> new EntityNotFoundException("Tutorial não encontrado"));
	}
	
	public Tutorial alter(Long id,TutorialRequest request) {
		Tutorial model = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tutorial não encontrado"));
		model.setTitle(request.getTitle());
		model.setUrl(toEmbedLink(request.getUrl()));
		return repository.save(model);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	private String toEmbedLink(String link) {
		return link.replace("youtu.be/", "www.youtube.com/embed/");
	}
	
	public String toLink(String embedLink) {
		return embedLink.replace("www.youtube.com/embed/","youtu.be/");
	}
}
