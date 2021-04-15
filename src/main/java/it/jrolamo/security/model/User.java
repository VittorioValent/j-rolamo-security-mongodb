package it.jrolamo.security.model;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import it.jrolamo.generics.mongodb.domain.AuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Document(collection = "user")
@EqualsAndHashCode(callSuper = false)
public class User extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String username;

    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean enabled;

    private boolean credentialsNonExpired;

    private Collection<Role> authorities;

}
