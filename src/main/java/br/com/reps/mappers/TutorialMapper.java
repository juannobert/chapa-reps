package br.com.reps.mappers;

import org.springframework.stereotype.Component;

import br.com.reps.dtos.requests.TutorialRequest;
import br.com.reps.dtos.responses.TutorialResponse;
import br.com.reps.entities.Tutorial;

@Component
public class TutorialMapper {

	
	public Tutorial toModel(TutorialRequest request) {
		Tutorial tutorial = new Tutorial();
		tutorial.setTitle(request.getTitle());
		tutorial.setUrl(request.getUrl());
		return tutorial;
	}
	
	public TutorialResponse toResponse(Tutorial model) {
		TutorialResponse response = new TutorialResponse();
		response.setId(model.getId());
		response.setTitle(model.getTitle());
		response.setUrl(model.getUrl());
		response.setDate(model.getDate());
		response.setAuthor(model.getAuthor());
		return response;
	}
	
}
