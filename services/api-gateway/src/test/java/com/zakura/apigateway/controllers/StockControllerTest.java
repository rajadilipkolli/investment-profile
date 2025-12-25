/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.zakura.apigateway.client.StockServiceClient;
import com.zakura.apigateway.data.TestData;
import com.zakura.apigateway.exception.DomainExceptionWrapper;
import com.zakura.apigateway.models.investment.Stock;
import com.zakura.apigateway.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(
        controllers = StockController.class,
        includeFilters = {
            @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    classes = DomainExceptionWrapper.class)
        })
@AutoConfigureWebTestClient
class StockControllerTest {

    @MockitoBean private StockServiceClient stockServiceClient;

    @MockitoBean private JwtTokenProvider jwtUtils;

    @Autowired private WebTestClient webTestClient;

    @Test
    @WithMockUser
    void testAvailableStocksToBuy() throws Exception {
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(stockServiceClient.getAvailableStocks())
                .thenReturn(Flux.fromIterable(TestData.getStockList()));
        webTestClient
                .get()
                .uri("/restservices/availableStocks")
                .header(TestData.AUTHORIZATION_STRING, TestData.AUTHORIZATION_HEADER_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Stock.class)
                .hasSize(4);
    }

    @Test
    void testAvailableStocksToBuyUnauthorized() {
        webTestClient
                .get()
                .uri("/restservices/availableStocks")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    void testAvailableStocksToBuyUnauthorized1() throws Exception {
        Mockito.when(stockServiceClient.getAvailableStocks())
                .thenReturn(Flux.fromIterable(TestData.getStockList()));
        webTestClient
                .get()
                .uri("/restservices/availableStocks")
                .header(TestData.AUTHORIZATION_STRING, TestData.INVALID_AUTHORIZATION_HEADER_VALUE)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    @WithMockUser
    void testBuyStock() throws Exception {

        final String body = TestData.getStockString();
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(stockServiceClient.saveUserStock(Mockito.any(), Mockito.anyString()))
                .thenReturn(Mono.just(TestData.SUCCESS));
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri("/restservices/buy/stock")
                .body(BodyInserters.fromValue(body))
                .headers(
                        httpHeaders -> {
                            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                            httpHeaders.add(
                                    TestData.AUTHORIZATION_STRING,
                                    TestData.INVALID_AUTHORIZATION_HEADER_VALUE);
                        })
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .consumeWith(
                        stringEntityExchangeResult ->
                                assertThat(stringEntityExchangeResult.getResponseBody())
                                        .isEqualTo("SUCCESS"));
    }
}
