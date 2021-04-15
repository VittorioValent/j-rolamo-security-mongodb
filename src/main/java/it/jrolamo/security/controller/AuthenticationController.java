package it.jrolamo.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import it.jrolamo.security.core.JWTUtils;
import it.jrolamo.security.core.LoginRequest;
import it.jrolamo.security.UserDTO;
import it.jrolamo.security.service.UserService;

/**
 * Controller that handles authentication and registration. With
 * {@link AuthenticationManager} saves the logged user in the security context.
 * 
 * 
 * @author JRolamo
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/auth/public")
@Api(value = "TicketManager", tags = "Protected Crud Controller: Operazioni di gestione Accessi e Registrazione")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        UserDTO user;
        try {
            user = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.ok(jwtTokenUtil.generateToken(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody LoginRequest user) throws Exception {
        try {
            userService.register(user);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private UserDTO authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return userService.loadUserByUsername(username);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}