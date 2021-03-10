package com.zakura.sipservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.zakura.sipservice.service.SipService;

import data.TestData;

@RunWith(MockitoJUnitRunner.class)
public class SipControllerTest {

	@InjectMocks
	private SipController sipControllerTest;

	@Mock
	private SipService sipService;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(sipControllerTest).build();
	}

	@Test
	public void testSaveUserStock() throws Exception {
		final String body = TestData.getSipVoString();
		Mockito.when(sipService.calculateSIP(Mockito.any())).thenReturn(BigDecimal.valueOf(1000));
		mockMvc.perform(post("/calculator/sip").content(body).param("request", body)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk()).andReturn();
	}
}
