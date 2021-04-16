package it.jrolamo.security.model.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import it.jrolamo.security.core.JWTUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class RegisterRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{username.notblank}")
    private String username;

    @NotBlank(message = "{password.notblank}")
    private String password;

    @NotBlank(message = "{email.notblank}")
    @Email(message = "{email.notvalid}")
    private String email;

}
