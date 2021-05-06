package it.jrolamo.security.service.mapper;

import it.jrolamo.generics.mongodb.mapper.IMapper;
import it.jrolamo.security.model.AbstractUser;
import it.jrolamo.security.model.dto.AbstractUserDTO;

public interface AbstractUserMapper<T extends AbstractUser, V extends AbstractUserDTO> extends IMapper<T, V> {
}
