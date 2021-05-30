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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
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

        //		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
        //			return ResponseEntity.badRequest().body(new MessageResponse("Email is already in
        // use!"));
        //		}
        //
        //		if (userRepository.existsByPan(signUpRequest.getPan())) {
        //			return ResponseEntity.badRequest().body(new MessageResponse("PAN number already in
        // use!"));
        //		}
        //
        //		if (userRepository.existsByPhone(signUpRequest.getPhone())) {
        //			return ResponseEntity.badRequest().body(new MessageResponse("Phone number already in
        // use!"));
        //		}

        // Create new user's account
        User user =
                new User(
                        signUpRequest.getFirstName(),
                        signUpRequest.getLastName(),
                        signUpRequest.getEmail(),
                        passwordEncoder.encode(signUpRequest.getPassword()),
                        signUpRequest.getPan(),
                        signUpRequest.getPhone());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            reactiveRoleRepository
                    .findByName(ERole.ROLE_USER)
                    .map(roles::add)
                    .switchIfEmpty(
                            Mono.error(() -> new RuntimeException("Error: Role is not found.")));
        } else {
            strRoles.forEach(
                    role -> {
                        switch (role) {
                            case "admin":
                                reactiveRoleRepository
                                        .findByName(ERole.ROLE_ADMIN)
                                        .map(roles::add)
                                        .switchIfEmpty(
                                                Mono.error(
                                                        () ->
                                                                new RuntimeException(
                                                                        "Error: Role is not found.")));
                                break;
                            case "mod":
                                reactiveRoleRepository
                                        .findByName(ERole.ROLE_MODERATOR)
                                        .map(roles::add)
                                        .switchIfEmpty(
                                                Mono.error(
                                                        () ->
                                                                new RuntimeException(
                                                                        "Error: Role is not found.")));

                                break;
                            default:
                                reactiveRoleRepository
                                        .findByName(ERole.ROLE_USER)
                                        .map(roles::add)
                                        .switchIfEmpty(
                                                Mono.error(
                                                        () ->
                                                                new RuntimeException(
                                                                        "Error: Role is not found.")));
                        }
                    });
        }

        user.setRoles(roles);
        reactiveUserRepository.save(user);

        if (signUpRequest.isReturnSecureToken()) {
            log.info("User registration Successfully!");
            return authenticateUser(
                    Mono.just(
                            LoginRequest.builder()
                                    .email(signUpRequest.getEmail())
                                    .password(signUpRequest.getPassword())
                                    .build()));
        }
        return Mono.just(ResponseEntity.ok(new MessageResponse("User registration unSuccessful!")));
    }
}
