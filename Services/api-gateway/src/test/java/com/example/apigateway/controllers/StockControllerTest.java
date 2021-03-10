package com.example.apigateway.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.apigateway.client.StockServiceClient;
import com.example.apigateway.data.TestData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(controllers = StockController.class)
class StockControllerTest extends AbstractControllerTest {

    @MockBean private StockServiceClient stockServiceClient;

    @Test
    public void testAvailableStocksToBuy() throws Exception {
        given(jwtUtils.getUserNameFromJwtToken(Mockito.anyString())).willReturn(TestData.USER_ID);
        given(stockServiceClient.getAvailableStocks()).willReturn(TestData.getStockList());
        mockMvc.perform(
                        get("/restservices/availableStocks")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header(
                                        TestData.AUTHORIZATION_STRING,
                                        TestData.AUTHORIZATION_HEADER_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testAvailableStocksToBuyUnauthorized() throws Exception {
        mockMvc.perform(
                        get("/restservices/availableStocks")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testAvailableStocksToBuyUnauthorized1() throws Exception {
        given(stockServiceClient.getAvailableStocks()).willReturn(TestData.getStockList());
        mockMvc.perform(
                        get("/restservices/availableStocks")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header(
                                        TestData.AUTHORIZATION_STRING,
                                        TestData.INVALID_AUTHORIZATION_HEADER_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testBuyStock() throws Exception {

        final String body = TestData.getStockString();
        given(jwtUtils.getUserNameFromJwtToken(Mockito.anyString())).willReturn(TestData.USER_ID);
        given(stockServiceClient.saveUserStock(Mockito.any(), Mockito.anyString()))
                .willReturn(TestData.SUCCESS);
        mockMvc.perform(
                        post("/restservices/buy/stock")
                                .content(body)
                                .param("request", body)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header(
                                        TestData.AUTHORIZATION_STRING,
                                        TestData.INVALID_AUTHORIZATION_HEADER_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
