/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.controllers;

import com.zakura.apigateway.aspect.LogMethodInvocationAndParams;
import com.zakura.apigateway.aspect.LogProcessTime;
import com.zakura.apigateway.payload.request.LoginRequest;
import com.zakura.apigateway.payload.request.SignupRequest;
import com.zakura.apigateway.payload.response.JwtResponse;
import com.zakura.apigateway.services.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @LogMethodInvocationAndParams
    @PostMapping("/signin")
    public Mono<ResponseEntity<JwtResponse>> authenticateUser(
            @Valid @RequestBody Mono<LoginRequest> loginRequest) {
        return authService.authenticateUser(loginRequest).map(ResponseEntity::ok);
    }

    @LogProcessTime
    @LogMethodInvocationAndParams
    @PostMapping("/signup")
    public Mono<ResponseEntity<JwtResponse>> registerUser(
            @Valid @RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest).map(ResponseEntity::ok);
    }
}
