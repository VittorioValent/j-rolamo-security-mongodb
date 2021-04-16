package it.jrolamo.security.model.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveUsernameRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Email(message = "{email.notvalid}")
    @NotBlank(message = "{email.notblank}")
    private String email;
}
