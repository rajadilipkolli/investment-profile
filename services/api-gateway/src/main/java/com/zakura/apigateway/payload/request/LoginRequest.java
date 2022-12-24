/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.payload.request;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 3102514387112202988L;

    @NotBlank private String email;

    @NotBlank private String password;
}
