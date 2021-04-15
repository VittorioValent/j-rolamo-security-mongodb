package it.jrolamo.security.service.mapper;

import org.mapstruct.Mapper;

import it.jrolamo.generics.mongodb.mapper.IMapper;
import it.jrolamo.security.model.User;
import it.jrolamo.security.model.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends IMapper<User, UserDTO> {
}
