/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String username;
    private final List<String> roles;
    private long expiresIn;

    public JwtResponse(String accessToken, String username, List<String> roles, long expiresIn) {
        this.accessToken = accessToken;
        this.username = username;
        this.roles = roles;
        this.expiresIn = expiresIn;
    }
}
