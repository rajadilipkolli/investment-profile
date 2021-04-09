package com.zakura.stockservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SwaggerConfigTest {

	@InjectMocks
	private SwaggerConfig swaggerConfig;

	@Test
	public void testApi() {
		swaggerConfig.api();
	}
}
