package com.example.apigateway.controllers;

import com.example.apigateway.security.jwt.AuthEntryPointJwt;
import com.example.apigateway.security.jwt.JwtUtils;
import com.example.apigateway.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WithMockUser
abstract class AbstractControllerTest {

    @MockBean private UserDetailsServiceImpl userDetailsService;

    @MockBean private AuthEntryPointJwt unauthorizedHandler;

    @MockBean protected JwtUtils jwtUtils;

    @Autowired protected MockMvc mockMvc;
}
