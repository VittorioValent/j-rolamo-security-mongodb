package it.jrolamo.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import it.jrolamo.security.core.JWTUtils;
import it.jrolamo.security.model.dto.AbstractUserDTO;
import it.jrolamo.security.model.dto.request.ChangePasswordRequest;
import it.jrolamo.security.model.dto.request.LoginRequest;
import it.jrolamo.security.model.dto.request.RegisterRequest;
import it.jrolamo.security.model.dto.request.ResetPasswordRequest;
import it.jrolamo.security.model.dto.request.RetrieveUsernameRequest;
import it.jrolamo.security.service.IUserService;

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
@Api(value = "AuthenticationController", tags = "Protected Crud Controller: Operazioni di gestione Accessi e Registrazione")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtTokenUtil;

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        AbstractUserDTO user;
        try {
            user = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getClass().getSimpleName() + " => " + e.getMessage());
        }
        return ResponseEntity.ok(jwtTokenUtil.generateToken(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) throws Exception {
        try {
            userService.register(request);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getClass().getSimpleName() + " => " + e.getMessage());
        }
    }

    @GetMapping("/register/confirm/{uuid}")
    public ResponseEntity<?> confirmRegister(@PathVariable("uuid") String uuid) throws Exception {
        try {
            userService.confirmRegister(uuid);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getClass().getSimpleName() + " => " + e.getMessage());
        }
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) throws Exception {
        try {
            userService.resetPassword(request);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getClass().getSimpleName() + " => " + e.getMessage());
        }
    }

    @PostMapping("/password/change")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) throws Exception {
        try {
            userService.changePassword(request);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getClass().getSimpleName() + " => " + e.getMessage());
        }
    }

    @PostMapping("/username/retrieve")
    public ResponseEntity<?> retrieveUsername(@Valid @RequestBody RetrieveUsernameRequest request) throws Exception {
        try {
            userService.retrieveUsername(request);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getClass().getSimpleName() + " => " + e.getMessage());
        }
    }

    private AbstractUserDTO authenticate(String username, String password) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return (AbstractUserDTO) userService.loadUserByUsername(username);
    }
}