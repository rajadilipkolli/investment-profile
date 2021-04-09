package com.zakura.apigateway.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String id;
	private String username;
	private String email;
	private final List<String> roles;
	private long expiresIn;

	public JwtResponse(String accessToken, String id, String username, String email, List<String> roles,
			long expiresIn) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.expiresIn = expiresIn;
	}


}
