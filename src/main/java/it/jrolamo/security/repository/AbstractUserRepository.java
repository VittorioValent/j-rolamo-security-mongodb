package it.jrolamo.security.repository;

import org.springframework.data.repository.NoRepositoryBean;

import it.jrolamo.generics.mongodb.repository.IPrivateRepository;
import it.jrolamo.security.model.AbstractUser;

@NoRepositoryBean
public interface AbstractUserRepository<T extends AbstractUser> extends IPrivateRepository<T> {

    public T findByUsername(String username);

    public T findByEmail(String email);

}
