package it.jrolamo.security.service.mapper;

import org.mapstruct.Mapper;

import it.jrolamo.generics.mongodb.mapper.IMapper;
import it.jrolamo.security.model.Role;
import it.jrolamo.security.model.dto.RoleDTO;

@Mapper(componentModel = "spring")
public interface RoleMapper extends IMapper<Role, RoleDTO> {
    
}
