/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zakura.apigateway.client.PortfolioServiceClient;
import com.zakura.apigateway.security.jwt.JwtTokenProvider;
import data.TestData;
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

@RunWith(MockitoJUnitRunner.class)
public class PortfolioControllerTest {

    @InjectMocks private PortfolioController portfolioController;

    @Mock private PortfolioServiceClient portfolioServiceClient;

    @Mock private JwtTokenProvider jwtUtils;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(portfolioController).build();
    }

    @Test
    public void testAllInvestments() throws Exception {
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(portfolioServiceClient.getAllInvestments(Mockito.anyString()))
                .thenReturn(TestData.getInvestmentList());
        mockMvc.perform(
                        get("/restservices//investments/all")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header(
                                        TestData.AUTHORIZATION_STRING,
                                        TestData.AUTHORIZATION_HEADER_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testUpdateInvestment() throws Exception {
        final String body = TestData.getInvestmentString();
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(
                        portfolioServiceClient.updateUserInvestment(
                                Mockito.any(), Mockito.anyString()))
                .thenReturn(TestData.getInvestment());
        mockMvc.perform(
                        post("/restservices/investments/update", new Object[] {TestData.USER_ID})
                                .content(body)
                                .param("request", body)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header(
                                        TestData.AUTHORIZATION_STRING,
                                        TestData.AUTHORIZATION_HEADER_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteInvestment() throws Exception {
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(
                        portfolioServiceClient.deleteUserInvestment(
                                Mockito.any(), Mockito.anyString()))
                .thenReturn(TestData.SUCCESS);
        final String body = TestData.getInvestmentString();
        mockMvc.perform(
                        post("/restservices/investments/delete")
                                .content(body)
                                .param("request", body)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header(
                                        TestData.AUTHORIZATION_STRING,
                                        TestData.AUTHORIZATION_HEADER_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testProfitInvestments() throws Exception {
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(portfolioServiceClient.getProfitInvestments(Mockito.anyString()))
                .thenReturn(TestData.getProfitInvestmentList());
        mockMvc.perform(
                        get("/restservices/investments/profit")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header(
                                        TestData.AUTHORIZATION_STRING,
                                        TestData.AUTHORIZATION_HEADER_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testLossInvestments() throws Exception {
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(portfolioServiceClient.getLossInvestments(Mockito.anyString()))
                .thenReturn(TestData.getLossInvestmentList());
        mockMvc.perform(
                        get("/restservices/investments/loss")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header(
                                        TestData.AUTHORIZATION_STRING,
                                        TestData.AUTHORIZATION_HEADER_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
