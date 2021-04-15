package it.jrolamo.security.repository;

import org.springframework.stereotype.Repository;

import it.jrolamo.generics.mongodb.repository.IRepository;
import it.jrolamo.security.User;

@Repository
public interface UserRepository extends IRepository<User> {

    public User findByUsername(String username);

}
