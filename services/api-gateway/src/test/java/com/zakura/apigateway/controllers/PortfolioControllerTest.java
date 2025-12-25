/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.zakura.apigateway.client.PortfolioServiceClient;
import com.zakura.apigateway.data.TestData;
import com.zakura.apigateway.exception.DomainExceptionWrapper;
import com.zakura.apigateway.models.investment.Investment;
import com.zakura.apigateway.security.jwt.JwtTokenProvider;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
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
        controllers = PortfolioController.class,
        includeFilters = {
            @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    classes = DomainExceptionWrapper.class)
        })
@WithMockUser
class PortfolioControllerTest {

    @MockitoBean private PortfolioServiceClient portfolioServiceClient;

    @MockitoBean private JwtTokenProvider jwtUtils;

    @Autowired private WebTestClient webTestClient;

    @Test
    void testAllInvestments() throws Exception {
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(portfolioServiceClient.getAllInvestments(Mockito.anyString()))
                .thenReturn(Flux.fromIterable(TestData.getInvestmentList()));
        webTestClient
                .get()
                .uri("/restservices//investments/all")
                .header(TestData.AUTHORIZATION_STRING, TestData.AUTHORIZATION_HEADER_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Investment.class)
                .hasSize(4);
    }

    @Test
    void testUpdateInvestment() throws Exception {
        final String body = TestData.getInvestmentString();
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(
                        portfolioServiceClient.updateUserInvestment(
                                Mockito.any(), Mockito.anyString()))
                .thenReturn(Mono.just(TestData.getInvestment()));
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri("/restservices/investments/update", new Object[] {TestData.USER_ID})
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
                .expectBody(Investment.class)
                .consumeWith(
                        investmentEntityExchangeResult -> {
                            var responseBody = investmentEntityExchangeResult.getResponseBody();
                            assertThat(responseBody).isNotNull();
                            assertThat(responseBody.getName()).isEqualTo("Mrf");
                            assertThat(responseBody.getType()).isEqualTo("Fix Deposit");
                            assertThat(responseBody.getQuantity()).isEqualTo(4);
                            assertThat(responseBody.getCostPrice()).isEqualTo(BigDecimal.ONE);
                            assertThat(responseBody.getCurrentPrice())
                                    .isEqualTo(BigDecimal.valueOf(30));
                            assertThat(responseBody.getProfitLossPercent()).isEqualTo(1.0f);
                            assertThat(responseBody.isProfit()).isEqualTo(true);
                        });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteInvestment() throws Exception {
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(
                        portfolioServiceClient.deleteUserInvestment(
                                Mockito.any(), Mockito.anyString()))
                .thenReturn(Mono.just(TestData.SUCCESS));
        final String body = TestData.getInvestmentString();
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri("/restservices/investments/delete")
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
                        investmentEntityExchangeResult ->
                                assertThat(investmentEntityExchangeResult.getResponseBody())
                                        .isEqualTo("SUCCESS"));
    }

    @Test
    void testProfitInvestments() throws Exception {
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(portfolioServiceClient.getProfitInvestments(Mockito.anyString()))
                .thenReturn(Flux.fromIterable(TestData.getProfitInvestmentList()));
        webTestClient
                .get()
                .uri("/restservices/investments/profit")
                .header(TestData.AUTHORIZATION_STRING, TestData.AUTHORIZATION_HEADER_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Investment.class)
                .hasSize(3);
    }

    @Test
    void testLossInvestments() throws Exception {
        Mockito.when(jwtUtils.getUsernameFromToken(Mockito.anyString()))
                .thenReturn(TestData.USER_ID);
        Mockito.when(portfolioServiceClient.getLossInvestments(Mockito.anyString()))
                .thenReturn(Flux.fromIterable(TestData.getLossInvestmentList()));
        webTestClient
                .get()
                .uri("/restservices/investments/loss")
                .header(TestData.AUTHORIZATION_STRING, TestData.AUTHORIZATION_HEADER_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Investment.class)
                .hasSize(1);
    }
}
