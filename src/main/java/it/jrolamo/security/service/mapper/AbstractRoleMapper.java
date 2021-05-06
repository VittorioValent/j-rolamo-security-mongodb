package it.jrolamo.security.service.mapper;

import it.jrolamo.generics.mongodb.mapper.IMapper;
import it.jrolamo.security.model.AbstractRole;
import it.jrolamo.security.model.dto.AbstractRoleDTO;

public interface AbstractRoleMapper<T extends AbstractRole, V extends AbstractRoleDTO> extends IMapper<T, V> {

}
