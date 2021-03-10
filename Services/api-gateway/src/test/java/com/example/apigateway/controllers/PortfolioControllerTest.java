package com.example.apigateway.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.apigateway.client.PortfolioServiceClient;
import com.example.apigateway.data.TestData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(controllers = PortfolioController.class)
class PortfolioControllerTest extends AbstractControllerTest {

    @MockBean private PortfolioServiceClient portfolioServiceClient;

    @Test
    public void testAllInvestments() throws Exception {
        Mockito.when(jwtUtils.getUserNameFromJwtToken(Mockito.anyString()))
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
        Mockito.when(jwtUtils.getUserNameFromJwtToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(
                        portfolioServiceClient.updateUserInvestment(
                                Mockito.any(), Mockito.anyString()))
                .thenReturn(TestData.getInvestment());
        mockMvc.perform(
                        post("/restservices/investments/update", TestData.USER_ID)
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
        Mockito.when(jwtUtils.getUserNameFromJwtToken(Mockito.anyString()))
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
        Mockito.when(jwtUtils.getUserNameFromJwtToken(Mockito.anyString()))
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
        Mockito.when(jwtUtils.getUserNameFromJwtToken(Mockito.anyString()))
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
