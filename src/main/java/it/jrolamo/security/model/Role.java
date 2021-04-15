package it.jrolamo.security.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import it.jrolamo.generics.mongodb.domain.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Document(collection = "role")
@EqualsAndHashCode(callSuper = false)
public class Role extends AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String authority;

}
