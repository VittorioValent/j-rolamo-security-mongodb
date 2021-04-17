package it.jrolamo.security.repository;

import org.springframework.stereotype.Repository;

import it.jrolamo.generics.mongodb.repository.IPrivateRepository;
import it.jrolamo.security.model.User;

@Repository
public interface UserRepository extends IPrivateRepository<User> {

    public User findByUsername(String username);

    public User findByEmail(String email);

}
