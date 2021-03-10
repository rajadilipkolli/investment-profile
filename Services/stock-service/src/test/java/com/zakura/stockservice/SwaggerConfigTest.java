package com.zakura.stockservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SwaggerConfigTest {

	@InjectMocks
	private SwaggerConfig swaggerConfig;

	@Test
	public void testApi() {
		swaggerConfig.api();
	}
}
