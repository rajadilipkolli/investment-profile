package com.example.sipservice.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.sipservice.service.SipService;
import data.TestData;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = SipController.class)
class SipControllerTest {

    @MockitoBean private SipService sipService;

    @Autowired private MockMvc mockMvc;

    @Test
    void testSaveUserStock() throws Exception {
        final String body = TestData.getSipVoString();
        given(sipService.calculateSIP(Mockito.any())).willReturn(BigDecimal.valueOf(1000));
        mockMvc.perform(
                        post("/calculator/sip")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
