package it.jrolamo.security;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import it.jrolamo.generics.mongodb.domain.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Document(collection = "role")
@EqualsAndHashCode(callSuper = false)
public class Role extends AbstractModel {

    @Id
    private String id;

    private String authority;

}
