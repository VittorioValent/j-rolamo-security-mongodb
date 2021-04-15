package it.jrolamo.security.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.jrolamo.generics.mongodb.service.ProtectedService;
import it.jrolamo.security.core.LoginRequest;
import it.jrolamo.security.model.Role;
import it.jrolamo.security.model.User;
import it.jrolamo.security.model.dto.UserDTO;
import it.jrolamo.security.repository.UserRepository;

@Service
public class UserService extends ProtectedService<User, UserDTO> implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        } else {
            return mapper.toDTO(user);
        }
    }

    /**
     * TODO LoginRequest -> RegistrationRequest (con mail e cacate varie)
     * 
     * TODO Impostare i ruoli e tutti gli altri parametri presenti in UserDetails
     * 
     * @param loginRequest
     */
    public void register(LoginRequest loginRequest) {
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        Role roleUser = new Role();
        roleUser.setAuthority("ROLE_USER");
        user.setAuthorities(Arrays.asList(roleUser));
        user = repository.save(user);
        user.setOwner(user.getUsername());
        repository.save(user);
    }

}
