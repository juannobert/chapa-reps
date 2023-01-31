package br.com.reps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.reps.dtos.requests.TutorialRequest;
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
	
	@Transactional
	public Tutorial insert(TutorialRequest request) {
		Tutorial entity = mapper.toModel(request);
		User author = securityUtils.getUsuarioLogado(); 
		entity.setAuthor(author);
		
		return repository.save(entity);
	}
}
