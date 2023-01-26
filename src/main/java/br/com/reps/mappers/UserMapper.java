package br.com.reps.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.reps.dtos.requests.UserRequest;
import br.com.reps.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
	
	User toModel(UserRequest request);

}
