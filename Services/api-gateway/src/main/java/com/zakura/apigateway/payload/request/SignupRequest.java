package com.zakura.apigateway.payload.request;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest implements Serializable {

    private static final long serialVersionUID = 8943941344586376658L;

    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(min = 10, max = 10)
    private String pan;

    /*
     * @NotBlank
     *
     * @Size(min = 10, max = 10)
     */
    private long phone;

    private Set<String> roles;

    private boolean returnSecureToken;
}
