package it.jrolamo.security.repository;

import org.springframework.stereotype.Repository;

import it.jrolamo.generics.mongodb.repository.IRepository;
import it.jrolamo.security.model.Role;

@Repository
public interface RoleRepository extends IRepository<Role> {

}
