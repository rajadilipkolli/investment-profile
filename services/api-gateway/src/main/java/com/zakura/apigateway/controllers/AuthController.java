/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.controllers;

import com.zakura.apigateway.aspect.LogMethodInvocationAndParams;
import com.zakura.apigateway.aspect.LogProcessTime;
import com.zakura.apigateway.models.ERole;
import com.zakura.apigateway.models.Role;
import com.zakura.apigateway.models.User;
import com.zakura.apigateway.payload.request.LoginRequest;
import com.zakura.apigateway.payload.request.SignupRequest;
import com.zakura.apigateway.payload.response.JwtResponse;
import com.zakura.apigateway.payload.response.MessageResponse;
import com.zakura.apigateway.repository.ReactiveRoleRepository;
import com.zakura.apigateway.repository.ReactiveUserRepository;
import com.zakura.apigateway.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ReactiveAuthenticationManager reactiveAuthenticationManager;

    private final ReactiveUserRepository reactiveUserRepository;

    private final ReactiveRoleRepository reactiveRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @LogMethodInvocationAndParams
    @PostMapping("/signin")
    public Mono<ResponseEntity<?>> authenticateUser(
            @Valid @RequestBody Mono<LoginRequest> loginRequest) {

        return loginRequest
                .flatMap(
                        login ->
                                this.reactiveAuthenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                login.getEmail(), login.getPassword())))
                .map(
                        authentication -> {
                            ReactiveSecurityContextHolder.withAuthentication(authentication);
                            String jwt = jwtTokenProvider.createToken(authentication);
                            Date expirationDate = jwtTokenProvider.getExpirationDateFromToken(jwt);
                            long expiresIn = expirationDate.getTime() - System.currentTimeMillis();
                            var userDetails =
                                    (org.springframework.security.core.userdetails.User)
                                            authentication.getPrincipal();
                            List<String> roles =
                                    userDetails.getAuthorities().stream()
                                            .map(GrantedAuthority::getAuthority)
                                            .collect(Collectors.toList());
                            return ResponseEntity.ok(
                                    new JwtResponse(
                                            jwt, userDetails.getUsername(), roles, expiresIn));
                        });
    }

    @LogProcessTime
    @LogMethodInvocationAndParams
    @PostMapping("/signup")
    public Mono<ResponseEntity<?>> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        return reactiveUserRepository
                .existsByEmail(signUpRequest.getEmail())
                .flatMap(
                        emailExists -> {
                            if (emailExists) {
                                return badResponseBuilder("Email is already in use!");
                            }
                            return reactiveUserRepository
                                    .existsByPan(signUpRequest.getPan())
                                    .flatMap(
                                            panExists ->
                                                    processAfterPanCheck(signUpRequest, panExists));
                        });
    }

    private Mono<? extends ResponseEntity<?>> processAfterPanCheck(
            SignupRequest signUpRequest, Boolean panExists) {
        if (panExists) {
            return badResponseBuilder("PAN number already in use!");
        }
        return reactiveUserRepository
                .existsByPhone(signUpRequest.getPhone())
                .flatMap(phoneExists -> processAfterPhoneCheck(signUpRequest, phoneExists));
    }

    private Mono<? extends ResponseEntity<?>> processAfterPhoneCheck(
            SignupRequest signUpRequest, Boolean phoneExists) {
        if (phoneExists) {
            return badResponseBuilder("Phone number already in use!");
        }
        // Create new user's account
        return createUser(signUpRequest)
                .flatMap(savedUser -> returnResponseMono(signUpRequest, savedUser));
    }

    private Mono<? extends ResponseEntity<?>> returnResponseMono(
            SignupRequest signUpRequest, User savedUser) {
        if (null != savedUser && signUpRequest.isReturnSecureToken()) {
            log.info("User registration Successfully!");
            return authenticateUser(
                    Mono.just(
                            LoginRequest.builder()
                                    .email(signUpRequest.getEmail())
                                    .password(signUpRequest.getPassword())
                                    .build()));
        }
        return badResponseBuilder("User registration unSuccessful!");
    }

    Mono<User> createUser(SignupRequest signUpRequest) {
        Mono<User> userMono =
                Mono.just(
                        new User(
                                signUpRequest.getFirstName(),
                                signUpRequest.getLastName(),
                                signUpRequest.getEmail(),
                                passwordEncoder.encode(signUpRequest.getPassword()),
                                signUpRequest.getPan(),
                                signUpRequest.getPhone()));

        Set<String> strRoles = signUpRequest.getRoles();

        Mono<List<Role>> rolesMono = Mono.just(new ArrayList<>());

        if (strRoles == null) {
            Mono<Role> userRoleMono = getUserRole(ERole.ROLE_USER);
            rolesMono =
                    rolesMono.flatMap(
                            roles ->
                                    userRoleMono.map(
                                            userRole -> {
                                                roles.add(userRole);
                                                return roles;
                                            }));
        } else {
            Mono<List<Role>> finalRolesMono = rolesMono;
            rolesMono =
                    Flux.fromIterable(strRoles)
                            .flatMap(
                                    role ->
                                            switch (role) {
                                                case "admin" -> getUserRole(ERole.ROLE_ADMIN);
                                                case "mod" -> getUserRole(ERole.ROLE_MODERATOR);
                                                default -> getUserRole(ERole.ROLE_USER);
                                            })
                            .collect(Collectors.toSet())
                            .flatMap(
                                    roles ->
                                            finalRolesMono.map(
                                                    existingRoles -> {
                                                        existingRoles.addAll(roles);
                                                        return existingRoles;
                                                    }));
        }

        return userMono.zipWith(
                        rolesMono,
                        (user, roles) -> {
                            user.setRoles(roles);
                            return user;
                        })
                .flatMap(reactiveUserRepository::save);
    }

    private static Mono<ResponseEntity<MessageResponse>> badResponseBuilder(String message) {
        return Mono.just(ResponseEntity.badRequest().body(new MessageResponse(message)));
    }

    private Mono<Role> getUserRole(ERole eRole) {
        return reactiveRoleRepository
                .findByName(eRole)
                .switchIfEmpty(Mono.error(new RuntimeException("Error: Role is not found.")));
    }
}
