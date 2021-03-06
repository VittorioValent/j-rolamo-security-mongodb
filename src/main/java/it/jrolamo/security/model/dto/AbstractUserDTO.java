package it.jrolamo.security.model.dto;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;

import it.jrolamo.generics.mongodb.domain.AuditDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AbstractUserDTO extends AuditDTO implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String password;

    private String email;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean enabled;

    private boolean credentialsNonExpired;

    private Collection<AbstractRoleDTO> authorities;
}
