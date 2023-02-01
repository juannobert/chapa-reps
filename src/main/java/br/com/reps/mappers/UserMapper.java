package br.com.reps.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.reps.dtos.requests.UserAdminRequest;
import br.com.reps.dtos.requests.UserDefaultRequest;
import br.com.reps.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	User toAdminModel(UserAdminRequest request);
	
	User toDefaultModel(UserDefaultRequest request);

}