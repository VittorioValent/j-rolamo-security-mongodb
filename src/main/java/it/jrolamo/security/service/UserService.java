package it.jrolamo.security.service;

import java.io.IOException;
import java.util.Arrays;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import freemarker.template.TemplateException;
import it.jrolamo.generics.mongodb.service.ProtectedService;
import it.jrolamo.security.mail.MailUtils;
import it.jrolamo.security.model.Role;
import it.jrolamo.security.model.User;
import it.jrolamo.security.model.dto.ChangePasswordRequest;
import it.jrolamo.security.model.dto.RegisterRequest;
import it.jrolamo.security.model.dto.UserDTO;
import it.jrolamo.security.repository.UserRepository;

@Service
public class UserService extends ProtectedService<User, UserDTO> implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MailUtils mailUtils;

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
     * @param request
     */
    public void register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        user.setEmail(request.getEmail().trim());
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

    /**
     * Generate new random password and send to email
     * 
     * @param username
     * @throws UsernameNotFoundException
     * @throws TemplateException
     * @throws IOException
     * @throws MessagingException
     */
    public void resetPassword(String username) throws UsernameNotFoundException, MessagingException, IOException, TemplateException {
        if(!StringUtils.isBlank(username)){
            UserDTO userDTO = loadUserByUsername(username);
            String newPassowrd = RandomStringUtils.randomAlphanumeric(8)+RandomStringUtils.randomAscii(4);
            userDTO.setPassword(passwordEncoder.encode(newPassowrd));
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom("copie@targetsolutions.it");
            mail.setText("Nuova password: "+newPassowrd+"\nRicordati di cambiarla per la miseria!!!");
            mail.setTo(userDTO.getEmail());
            mail.setSubject("Recupero Password Fake");
            mailUtils.sendSimpleMessage(mail); 
            super.update(userDTO);
        }else{
            throw new UsernameNotFoundException("Username vuota, nun ce provà...");
        }
    }

    public void changePassword(ChangePasswordRequest request) throws AuthenticationException, BadCredentialsException, MessagingException, IOException, TemplateException, UsernameNotFoundException {
        if(!request.getOldPassword().trim().equals(request.getNewPassword().trim())){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getOldPassword()));
            UserDTO userDTO = loadUserByUsername(request.getUsername());
            userDTO.setPassword(passwordEncoder.encode(request.getNewPassword()));
            
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom("copie@targetsolutions.it");
            mail.setText("La tua password è stata modificata\nSe non sei stato tu...Preoccupati perché...STANNO ARRIVANDO!!!");
            mail.setTo(userDTO.getEmail());
            mail.setSubject("Modifica Password Fake");
            mailUtils.sendSimpleMessage(mail); 
            super.update(userDTO);
        }else{
            throw new BadCredentialsException("Sei furbo solo te...");
        }
    }
}
