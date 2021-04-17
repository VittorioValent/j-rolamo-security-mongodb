package it.jrolamo.security.model.dto;

import org.springframework.security.core.GrantedAuthority;

import it.jrolamo.generics.mongodb.domain.AuditDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDTO extends AuditDTO implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String id;

    private String authority;
}
