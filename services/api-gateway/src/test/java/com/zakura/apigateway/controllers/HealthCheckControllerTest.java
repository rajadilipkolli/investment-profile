/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.zakura.apigateway.exception.DomainExceptionWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(
        controllers = HealthCheckController.class,
        includeFilters = {
            @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    classes = DomainExceptionWrapper.class)
        })
@AutoConfigureWebTestClient
class HealthCheckControllerTest {

    @Autowired private WebTestClient webTestClient;

    @Test
    @WithMockUser
    void testHealthCheck() {
        webTestClient
                .get()
                .uri("/health-check/status")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .consumeWith(
                        response ->
                                assertThat(response.getResponseBody())
                                        .isEqualTo("API Service is UP!"));
    }
}
