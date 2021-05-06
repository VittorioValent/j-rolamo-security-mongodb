package it.jrolamo.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import it.jrolamo.generics.mongodb.service.PrivateService;
import it.jrolamo.security.model.AbstractRole;
import it.jrolamo.security.model.dto.AbstractRoleDTO;

@Service
public abstract class AbstractRoleService<T extends AbstractRole, V extends AbstractRoleDTO>
        extends PrivateService<T, V> {

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public V create(V dto) {
        return super.create(dto);
    }

}
