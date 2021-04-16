package it.jrolamo.security.model.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{username.notnull}")
    @NotEmpty(message = "{username.notnull}")
    private String username;

    @NotNull(message = "{password.notnull}")
    @NotEmpty(message = "{password.notnull}")
    private String oldPassword;

    @NotNull(message = "{password.notnull}")
    @NotEmpty(message = "{password.notnull}")
    private String newPassword;

}
