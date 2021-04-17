package it.jrolamo.security.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import it.jrolamo.generics.mongodb.domain.AuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Document(collection = "role")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Role extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String authority;

}
