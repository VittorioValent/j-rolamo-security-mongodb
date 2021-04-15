package it.jrolamo.security.service;

import org.springframework.stereotype.Service;

import it.jrolamo.generics.mongodb.service.PublicService;
import it.jrolamo.security.Role;
import it.jrolamo.security.RoleDTO;

@Service
public class RoleService extends PublicService<Role, RoleDTO> {

}
