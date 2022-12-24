/* Licensed under Apache-2.0 2022 */
package com.zakura.apigateway.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zakura.apigateway.client.StockServiceClient;
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
public class StockControllerTest {

    @InjectMocks private StockController stockController;

    @Mock private StockServiceClient stockServiceClient;

    @Mock private JwtTokenProvider jwtUtils;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
    }

    @Test
    public void testAvailableStocksToBuy() throws Exception {
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(stockServiceClient.getAvailableStocks()).thenReturn(TestData.getStockList());
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

    @Test(expected = AssertionError.class)
    public void testAvailableStocksToBuyUnauthorized() throws Exception {
        mockMvc.perform(
                        get("/restservices/availableStocks")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testAvailableStocksToBuyUnauthorized1() throws Exception {
        Mockito.when(stockServiceClient.getAvailableStocks()).thenReturn(TestData.getStockList());
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
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(stockServiceClient.saveUserStock(Mockito.any(), Mockito.anyString()))
                .thenReturn(TestData.SUCCESS);
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
