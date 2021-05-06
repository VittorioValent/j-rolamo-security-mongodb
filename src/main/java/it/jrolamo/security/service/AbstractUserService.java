package it.jrolamo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.jrolamo.generics.mongodb.service.PrivateService;
import it.jrolamo.security.model.AbstractUser;
import it.jrolamo.security.model.dto.AbstractUserDTO;
import it.jrolamo.security.repository.AbstractUserRepository;

@Service
public abstract class AbstractUserService<T extends AbstractUser, V extends AbstractUserDTO>
        extends PrivateService<T, V> implements IUserService {

    @Autowired
    private AbstractUserRepository<T> userRepository;

    @Override
    public V loadUserByUsername(String username) throws UsernameNotFoundException {
        T user = userRepository.findByUsername(username.trim());
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        } else {
            return mapper.toDTO(user);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public V create(V dto) {
        return super.create(dto);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public V update(V dto) {
        return super.update(dto);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public V merge(String id, V dto) {
        return super.merge(id, dto);
    }

}
