package br.com.reps.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.reps.dtos.requests.TutorialRequest;
import br.com.reps.dtos.responses.TutorialResponse;
import br.com.reps.entities.Tutorial;
import br.com.reps.entities.User;
import br.com.reps.mappers.TutorialMapper;
import br.com.reps.repositories.TutorialRepository;
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
		Tutorial entity = mapper.toModel(request);
		User author = securityUtils.getUsuarioLogado(); 
		entity.setDate(LocalDateTime.now());
		entity.setAuthor(author);
		String youtubeLink = entity.getUrl();
		String embedLink = youtubeLink.replace("youtu.be/", "www.youtube.com/embed/");
		entity.setUrl(embedLink);
		
		return repository.save(entity);
	}
}
