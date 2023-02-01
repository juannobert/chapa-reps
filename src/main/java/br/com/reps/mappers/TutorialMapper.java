package br.com.reps.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.reps.dtos.requests.TutorialRequest;
import br.com.reps.dtos.responses.TutorialResponse;
import br.com.reps.entities.Tutorial;

@Mapper(componentModel = "spring")
public interface TutorialMapper {

	TutorialMapper INSTANCE = Mappers.getMapper(TutorialMapper.class);
	
	Tutorial toModel(TutorialRequest request);
	
	TutorialResponse toResponse(Tutorial model);
	
}
