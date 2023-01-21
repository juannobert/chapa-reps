package br.com.reps.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.reps.dtos.requests.PostRequest;
import br.com.reps.dtos.requests.SupportRequest;
import br.com.reps.dtos.responses.PostResponse;
import br.com.reps.dtos.responses.SupportResponse;
import br.com.reps.entities.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {
	
	PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
	
	PostResponse toResponse(Post post);
	
	Post toModel(PostRequest request);

	SupportResponse toSupportResponse(Post post);
	
	Post toModel(SupportRequest request);
}
