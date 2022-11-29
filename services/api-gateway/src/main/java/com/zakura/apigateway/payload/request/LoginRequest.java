package com.zakura.apigateway.payload.request;

import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 3102514387112202988L;

    @NotBlank private String email;

    @NotBlank private String password;
}
