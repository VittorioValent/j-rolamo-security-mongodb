package it.jrolamo.security.service;

import java.io.IOException;
import java.util.Arrays;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import freemarker.template.TemplateException;
import it.jrolamo.generics.mongodb.service.PrivateService;
import it.jrolamo.security.mail.MailUtils;
import it.jrolamo.security.model.Role;
import it.jrolamo.security.model.User;
import it.jrolamo.security.model.dto.UserDTO;
import it.jrolamo.security.model.dto.request.ChangePasswordRequest;
import it.jrolamo.security.model.dto.request.RegisterRequest;
import it.jrolamo.security.model.dto.request.ResetPasswordRequest;
import it.jrolamo.security.model.dto.request.RetrieveUsernameRequest;
import it.jrolamo.security.repository.UserRepository;

@Service
public class UserService extends PrivateService<User, UserDTO> implements UserDetailsService {

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
        User user = userRepository.findByUsername(username.trim());
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        } else {
            return mapper.toDTO(user);
        }
    }

    // TODO: trovare un metodo intelligente per fare l'auto trim delle stringhe
    // nelle request

    /**
     * @param request
     * @throws TemplateException
     * @throws IOException
     * @throws MessagingException
     */
    public void register(RegisterRequest request) throws MessagingException, IOException, TemplateException {
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
        // in order to set correct audit
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword()));
        user.setOwner(user.getUsername());
        user.setEnabled(false);
        user = repository.save(user);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(mailUtils.getFrom());
        mail.setText(
                "Per completare la registrazione al nosto fantastico portale clicca al seguente link: http://localhost:18019/auth/public/register/confirm/"
                        + user.getId());
        mail.setTo(user.getEmail());
        mail.setSubject("Conferma registrazione");
        mailUtils.sendSimpleMessage(mail);
    }

    /**
     * Generate new random password and send to email
     * 
     * @param request
     * @throws UsernameNotFoundException
     * @throws TemplateException
     * @throws IOException
     * @throws MessagingException
     */
    public void resetPassword(ResetPasswordRequest request)
            throws UsernameNotFoundException, MessagingException, IOException, TemplateException {
        if (!StringUtils.isBlank(request.getUsername())) {
            UserDTO userDTO = loadUserByUsername(request.getUsername().trim());
            String newPassowrd = RandomStringUtils.randomAlphanumeric(8) + RandomStringUtils.randomAscii(4);
            userDTO.setPassword(passwordEncoder.encode(newPassowrd));
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(mailUtils.getFrom());
            mail.setText("Nuova password: " + newPassowrd + "\nRicordati di cambiarla per la miseria!!!");
            mail.setTo(userDTO.getEmail());
            mail.setSubject("Recupero Password Fake");
            mailUtils.sendSimpleMessage(mail);
            super.update(userDTO);
        } else {
            throw new UsernameNotFoundException("Username vuota, nun ce provà...");
        }
    }

    public void changePassword(ChangePasswordRequest request) throws AuthenticationException, BadCredentialsException,
            MessagingException, IOException, TemplateException, UsernameNotFoundException {
        if (!request.getOldPassword().trim().equals(request.getNewPassword().trim())) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getOldPassword()));
            UserDTO userDTO = loadUserByUsername(request.getUsername());
            userDTO.setPassword(passwordEncoder.encode(request.getNewPassword()));

            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(mailUtils.getFrom());
            mail.setText(
                    "La tua password è stata modificata\nSe non sei stato tu...Preoccupati perché...STANNO ARRIVANDO!!!");
            mail.setTo(userDTO.getEmail());
            mail.setSubject("Modifica Password Fake");
            mailUtils.sendSimpleMessage(mail);
            super.update(userDTO);
        } else {
            throw new BadCredentialsException("Sei furbo solo te...");
        }
    }

    public void retrieveUsername(RetrieveUsernameRequest request)
            throws UsernameNotFoundException, MessagingException, IOException, TemplateException {
        if (!StringUtils.isBlank(request.getEmail())) {
            User user = userRepository.findByEmail(request.getEmail().trim());
            if (user == null)
                throw new UsernameNotFoundException("Email non presente nei nostri fantastici archivi");
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(mailUtils.getFrom());
            mail.setText("La tua username è sempre stata: " + user.getUsername()
                    + "\nSegnatela su un foglio vicino al monitor in bella vista...");
            mail.setTo(user.getEmail());
            mail.setSubject("Recupero Username Fake");
            mailUtils.sendSimpleMessage(mail);
        } else {
            throw new UsernameNotFoundException("Email vuota, nun ce provà...");
        }
    }

    public void confirmRegister(String uuid) throws Exception {
        UserDTO userDTO = super.read(uuid);
        if (userDTO != null) {
            userDTO.setEnabled(true);
            super.update(userDTO);
        } else {
            throw new Exception("Uuid not found or fake");
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDTO create(UserDTO dto) {
        return super.create(dto);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDTO update(UserDTO dto) {
        return super.update(dto);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDTO merge(String id, UserDTO dto) {
        return super.merge(id, dto);
    }

}
