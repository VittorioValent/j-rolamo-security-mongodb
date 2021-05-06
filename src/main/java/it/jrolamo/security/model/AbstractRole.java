package it.jrolamo.security.model;

import org.springframework.data.annotation.Id;

import it.jrolamo.generics.mongodb.domain.AuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class AbstractRole extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String authority;

}
