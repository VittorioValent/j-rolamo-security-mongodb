package it.jrolamo.security.model.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{username.notblank}")
    private String username;
}
