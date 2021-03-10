package com.example.apigateway.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SwaggerConfigTest {

    @InjectMocks private SwaggerConfig swaggerConfig;

    @Test
    void testApi() {
        swaggerConfig.api();
    }
}
