package com.zakura.apigateway.payload.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private final List<String> roles;
    private long expiresIn;

    public JwtResponse(String accessToken, String username, List<String> roles, long expiresIn) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
        this.expiresIn = expiresIn;
    }
}
