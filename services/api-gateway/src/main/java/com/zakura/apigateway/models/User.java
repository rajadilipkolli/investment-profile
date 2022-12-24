/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@ToString
@Getter
@Setter
public class User {

    @Id private String id;

    @NotBlank
    @Size(max = 20)
    private String firstName;

    @NotBlank
    @Size(max = 20)
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(min = 10, max = 10)
    private String pan;

    @NotBlank
    @Size(min = 10, max = 10)
    private long phone;

    private boolean active = true;

    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(
            String firstName,
            String lastName,
            String email,
            String password,
            String pan,
            long phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.pan = pan;
        this.phone = phoneNumber;
    }
}
