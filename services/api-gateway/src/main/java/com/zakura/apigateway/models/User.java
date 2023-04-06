/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.models;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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

    @Min(value = 5999_999_999L)
    @Max(value = 9999_999_999L)
    private Long phone;

    private boolean active = true;

    private List<Role> roles = new ArrayList<>();

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
