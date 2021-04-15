package it.jrolamo.security;

import org.mapstruct.Mapper;

import it.jrolamo.generics.mongodb.mapper.IMapper;
import it.jrolamo.security.Role;
import it.jrolamo.security.RoleDTO;

@Mapper(componentModel = "spring")
public interface RoleMapper extends IMapper<Role, RoleDTO> {
    
}
