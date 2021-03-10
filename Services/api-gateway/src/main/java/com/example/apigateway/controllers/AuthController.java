package com.example.apigateway.controllers;

import com.example.apigateway.aspect.LogMethodInvocationAndParams;
import com.example.apigateway.aspect.LogProcessTime;
import com.example.apigateway.models.ERole;
import com.example.apigateway.models.Role;
import com.example.apigateway.models.User;
import com.example.apigateway.payload.request.LoginRequest;
import com.example.apigateway.payload.request.SignupRequest;
import com.example.apigateway.payload.response.JwtResponse;
import com.example.apigateway.payload.response.MessageResponse;
import com.example.apigateway.repository.RoleRepository;
import com.example.apigateway.repository.UserRepository;
import com.example.apigateway.security.jwt.JwtUtils;
import com.example.apigateway.security.services.UserDetailsImpl;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired AuthenticationManager authenticationManager;

    @Autowired UserRepository userRepository;

    @Autowired RoleRepository roleRepository;

    @Autowired PasswordEncoder encoder;

    @Autowired JwtUtils jwtUtils;

    @LogMethodInvocationAndParams
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        Date expirationDate = jwtUtils.getExpirationDateFromToken(jwt);
        long expiresIn = expirationDate.getTime() - System.currentTimeMillis();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles =
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles,
                        expiresIn));
    }

    @LogProcessTime
    @LogMethodInvocationAndParams
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Email is already in use!"));
        }

        if (userRepository.existsByPan(signUpRequest.getPan())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("PAN number already in use!"));
        }

        if (userRepository.existsByPhone(signUpRequest.getPhone())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Phone number already in use!"));
        }

        // Create new user's account
        User user =
                new User(
                        signUpRequest.getFirstName(),
                        signUpRequest.getLastName(),
                        signUpRequest.getEmail(),
                        encoder.encode(signUpRequest.getPassword()),
                        signUpRequest.getPan(),
                        signUpRequest.getPhone());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (CollectionUtils.isEmpty(strRoles)) {
            Role userRole =
                    roleRepository
                            .findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(
                    role -> {
                        switch (role) {
                            case "admin":
                                Role adminRole =
                                        roleRepository
                                                .findByName(ERole.ROLE_ADMIN)
                                                .orElseThrow(
                                                        () ->
                                                                new RuntimeException(
                                                                        "Error: Role is not found."));
                                roles.add(adminRole);

                                break;
                            case "mod":
                                Role modRole =
                                        roleRepository
                                                .findByName(ERole.ROLE_MODERATOR)
                                                .orElseThrow(
                                                        () ->
                                                                new RuntimeException(
                                                                        "Error: Role is not found."));
                                roles.add(modRole);

                                break;
                            default:
                                Role userRole =
                                        roleRepository
                                                .findByName(ERole.ROLE_USER)
                                                .orElseThrow(
                                                        () ->
                                                                new RuntimeException(
                                                                        "Error: Role is not found."));
                                roles.add(userRole);
                        }
                    });
        }

        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        if (savedUser != null && signUpRequest.isReturnSecureToken()) {
            log.info("User registration Successfully!");
            return authenticateUser(
                    LoginRequest.builder()
                            .email(signUpRequest.getEmail())
                            .password(signUpRequest.getPassword())
                            .build());
        }
        return ResponseEntity.ok(new MessageResponse("User registration unSuccessfull!"));
    }
}
