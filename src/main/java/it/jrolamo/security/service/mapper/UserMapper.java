package it.jrolamo.security;

import org.mapstruct.Mapper;

import it.jrolamo.generics.mongodb.mapper.IMapper;
import it.jrolamo.security.User;
import it.jrolamo.security.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends IMapper<User, UserDTO> {
}
