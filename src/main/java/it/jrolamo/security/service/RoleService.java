package it.jrolamo.security.service;

import org.springframework.stereotype.Service;

import it.jrolamo.generics.mongodb.service.PublicService;
import it.jrolamo.security.model.Role;
import it.jrolamo.security.model.dto.RoleDTO;

@Service
public class RoleService extends PublicService<Role, RoleDTO> {

}
