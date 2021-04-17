package it.jrolamo.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import it.jrolamo.generics.mongodb.service.PrivateService;
import it.jrolamo.security.model.Role;
import it.jrolamo.security.model.dto.RoleDTO;

@Service
public class RoleService extends PrivateService<Role, RoleDTO> {

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public RoleDTO create(RoleDTO dto) {
        System.out.println("TEST CREATE ROLE");
        return super.create(dto);
    }

}
