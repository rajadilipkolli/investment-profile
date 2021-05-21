package com.zakura.stockservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class HealthCheckControllerTest {

	@InjectMocks
	private HealthCheckController healthCheckController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(healthCheckController).build();
	}

	@Test
	void testHealthCheck() throws Exception {
		mockMvc.perform(get("/health-check/status").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
	}

}
