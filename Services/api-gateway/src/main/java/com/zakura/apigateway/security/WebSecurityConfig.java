package com.zakura.apigateway.security;

import com.zakura.apigateway.repository.ReactiveUserRepository;
import com.zakura.apigateway.security.jwt.JwtTokenAuthenticationFilter;
import com.zakura.apigateway.security.jwt.JwtTokenProvider;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
        // -- Swagger UI v3 (OpenAPI)
        "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**", "/auth/**", "/health-check/**"
        // other public endpoints of your API may be appended to this array
    };

    @Bean
    public SecurityWebFilterChain springWebFilterChain(
            ServerHttpSecurity http,
            JwtTokenProvider tokenProvider,
            ReactiveAuthenticationManager reactiveAuthenticationManager) {

        final String authenticationPaths = "/auth/**";

        return http.authenticationManager(reactiveAuthenticationManager)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(
                        it ->
                                it.pathMatchers(HttpMethod.GET, AUTH_WHITELIST)
                                        .permitAll()
                                        .pathMatchers(HttpMethod.POST, authenticationPaths)
                                        .permitAll()
                                        .pathMatchers(HttpMethod.DELETE, authenticationPaths)
                                        .hasRole("ADMIN")
                                        .matchers(
                                                PathRequest.toStaticResources().atCommonLocations())
                                        .permitAll()
                                        .pathMatchers(HttpMethod.OPTIONS)
                                        .permitAll()
                                        .anyExchange()
                                        .authenticated())
                .addFilterAt(
                        new JwtTokenAuthenticationFilter(tokenProvider),
                        SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin()
                .disable()
                .logout()
                .disable()
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(
            ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        var authenticationManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(ReactiveUserRepository userRepository) {

        return username ->
                userRepository
                        .findByEmail(username)
                        .map(
                                u ->
                                        User.withUsername(u.getEmail())
                                                .password(u.getPassword())
                                                .authorities(
                                                        u.getRoles().stream()
                                                                .map(role -> role.getName().name())
                                                                .map(SimpleGrantedAuthority::new)
                                                                .collect(Collectors.toList()))
                                                .accountExpired(!u.isActive())
                                                .credentialsExpired(!u.isActive())
                                                .disabled(!u.isActive())
                                                .accountLocked(!u.isActive())
                                                .build());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
