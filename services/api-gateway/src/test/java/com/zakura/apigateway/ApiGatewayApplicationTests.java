/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway;

import static org.assertj.core.api.Assertions.assertThat;

import com.zakura.apigateway.data.TestData;
import com.zakura.apigateway.payload.request.SignupRequest;
import com.zakura.apigateway.payload.response.JwtResponse;
import com.zakura.apigateway.payload.response.MessageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(properties = {"spring.cloud.service-registry.auto-registration.enabled=false"})
@AutoConfigureWebTestClient
class ApiGatewayApplicationTests extends AbstractMongoDBTestContainer {

    @Autowired private WebTestClient webTestClient;

    @Test
    void contextLoads() {
        assertThat(MONGO_DB_CONTAINER.isRunning()).isTrue();
    }

    @Test
    void testSignUp() {
        SignupRequest signUpRequest = TestData.getSignupRequest();
        this.webTestClient
                .post()
                .uri("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(signUpRequest))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(JwtResponse.class)
                .consumeWith(
                        jwtResponseEntityExchangeResult -> {
                            assertThat(jwtResponseEntityExchangeResult.getResponseBody())
                                    .isNotNull();
                            assertThat(jwtResponseEntityExchangeResult.getResponseBody().getRoles())
                                    .isNotEmpty()
                                    .hasSize(2)
                                    .contains("ROLE_ADMIN", "ROLE_USER");
                        });

        // attempting to save same request should result in badRequest
        this.webTestClient
                .post()
                .uri("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(signUpRequest))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(MessageResponse.class)
                .consumeWith(
                        messageResponseEntityExchangeResult -> {
                            assertThat(messageResponseEntityExchangeResult.getResponseBody())
                                    .isNotNull();
                            assertThat(
                                            messageResponseEntityExchangeResult
                                                    .getResponseBody()
                                                    .message())
                                    .isEqualTo("Email is already in use!");
                        });

        // changing email with same request should fail
        signUpRequest.setEmail("junit2@email.com");
        this.webTestClient
                .post()
                .uri("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(signUpRequest))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(MessageResponse.class)
                .consumeWith(
                        messageResponseEntityExchangeResult -> {
                            assertThat(messageResponseEntityExchangeResult.getResponseBody())
                                    .isNotNull();
                            assertThat(
                                            messageResponseEntityExchangeResult
                                                    .getResponseBody()
                                                    .message())
                                    .isEqualTo("PAN number already in use!");
                        });

        // changing pan and email with same request should fail
        signUpRequest.setPan("ABCDE9876F");
        this.webTestClient
                .post()
                .uri("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(signUpRequest))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(MessageResponse.class)
                .consumeWith(
                        messageResponseEntityExchangeResult -> {
                            assertThat(messageResponseEntityExchangeResult.getResponseBody())
                                    .isNotNull();
                            assertThat(
                                            messageResponseEntityExchangeResult
                                                    .getResponseBody()
                                                    .message())
                                    .isEqualTo("Phone number already in use!");
                        });

        // changing pan and email with same request should fail
        signUpRequest.setPan("ABCDE9876F");
        this.webTestClient
                .post()
                .uri("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(signUpRequest))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(MessageResponse.class)
                .consumeWith(
                        messageResponseEntityExchangeResult -> {
                            assertThat(messageResponseEntityExchangeResult.getResponseBody())
                                    .isNotNull();
                            assertThat(
                                            messageResponseEntityExchangeResult
                                                    .getResponseBody()
                                                    .message())
                                    .isEqualTo("Phone number already in use!");
                        });

        // changing phone number as well should pass
        signUpRequest.setPhone(9999988888L);
        this.webTestClient
                .post()
                .uri("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(signUpRequest))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(JwtResponse.class)
                .consumeWith(
                        jwtResponseEntityExchangeResult -> {
                            assertThat(jwtResponseEntityExchangeResult.getResponseBody())
                                    .isNotNull();
                            assertThat(jwtResponseEntityExchangeResult.getResponseBody().getRoles())
                                    .isNotEmpty()
                                    .hasSize(2)
                                    .contains("ROLE_ADMIN", "ROLE_USER");
                        });
    }
}
