/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.payload.request;

import jakarta.validation.constraints.*;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
public class SignupRequest implements Serializable {

    @Serial private static final long serialVersionUID = 8943941344586376658L;

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

    @Min(value = 5999_999_999L)
    @Max(value = 9999_999_999L)
    private Long phone;

    private Set<String> roles;

    private boolean returnSecureToken;
}
