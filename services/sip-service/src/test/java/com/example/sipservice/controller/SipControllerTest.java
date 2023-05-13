package com.example.sipservice.controller;

import data.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.example.sipservice.service.SipService;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = SipController.class)
public class SipControllerTest {

	@MockBean
	private SipService sipService;

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testSaveUserStock() throws Exception {
		final String body = TestData.getSipVoString();
		given(sipService.calculateSIP(Mockito.any())).willReturn(BigDecimal.valueOf(1000));
		mockMvc.perform(post("/calculator/sip")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk()).andReturn();
	}
}
