package it.jrolamo.security.repository;

import org.springframework.data.repository.NoRepositoryBean;

import it.jrolamo.generics.mongodb.repository.IPrivateRepository;
import it.jrolamo.security.model.AbstractRole;

@NoRepositoryBean
public interface AbstractRoleRepository<T extends AbstractRole> extends IPrivateRepository<T> {

}
