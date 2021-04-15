package it.jrolamo.security.model.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import it.jrolamo.security.core.JWTUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Container and validation Class for username and password
 * 
 * @author JRolamo
 *
 * @see JWTUtils
 * @see JWTResponse
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class RegisterRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{username.notnull}")
    @NotEmpty(message = "{username.notnull}")
    @NotBlank(message = "{username.notnull}")
    private String username;

    @NotNull(message = "{password.notnull}")
    @NotEmpty(message = "{password.notnull}")
    @NotBlank(message = "{password.notnull}")
    private String password;

    @NotNull(message = "{email.notnull}")
    @NotEmpty(message = "{email.notnull}")
    @Email(message = "{email.notvalid}")
    private String email;
}
