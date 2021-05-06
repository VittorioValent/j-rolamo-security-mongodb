package it.jrolamo.security.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.jrolamo.security.model.dto.request.ChangePasswordRequest;
import it.jrolamo.security.model.dto.request.RegisterRequest;
import it.jrolamo.security.model.dto.request.ResetPasswordRequest;
import it.jrolamo.security.model.dto.request.RetrieveUsernameRequest;

@Service
public interface IUserService extends UserDetailsService {

    /**
     * @param request
     * @throws Exception
     */
    public void register(RegisterRequest request) throws Exception;

    /**
     * Generate new random password and send to email
     * 
     * @param request
     * @throws UsernameNotFoundException
     * @throws Exception
     */
    public abstract void resetPassword(ResetPasswordRequest request) throws UsernameNotFoundException, Exception;

    public abstract void changePassword(ChangePasswordRequest request)
            throws AuthenticationException, BadCredentialsException, UsernameNotFoundException, Exception;

    public abstract void retrieveUsername(RetrieveUsernameRequest request) throws UsernameNotFoundException, Exception;

    public abstract void confirmRegister(String uuid) throws Exception;

}
