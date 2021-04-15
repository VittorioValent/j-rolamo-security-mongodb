package it.jrolamo.security;

import org.springframework.security.core.GrantedAuthority;

import it.jrolamo.generics.mongodb.domain.AbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDTO extends AbstractDTO implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String id;

    private String authority;
}