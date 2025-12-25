/* Licensed under Apache-2.0 2025 */
package com.zakura.portfolioservice;

import java.time.Duration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.grafana.LgtmStackContainer;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestPortfolioServiceApplication {

    @Bean
    @ServiceConnection
    MongoDBContainer mongoDbContainer() {
        return new MongoDBContainer(DockerImageName.parse("mongo").withTag("8.2.3"))
                .withSharding()
                .withStartupAttempts(3)
                .withStartupTimeout(Duration.ofMinutes(2));
    }

    @Bean
    @ServiceConnection
    LgtmStackContainer lgtmContainer() {
        return new LgtmStackContainer(DockerImageName.parse("grafana/otel-lgtm:0.13.0"))
                .withStartupTimeout(Duration.ofMinutes(2));
    }

    @Bean
    GenericContainer<?> discoveryServiceContainer() {
        return new GenericContainer<>("dockertmt/discovery-service").withExposedPorts(8761);
    }

    @Bean
    DynamicPropertyRegistrar dynamicPropertyRegistrar(
            GenericContainer<?> discoveryServiceContainer) {
        return dynamicPropertyRegistry -> {
            dynamicPropertyRegistry.add(
                    "eureka.client.service-url.defaultZone",
                    () ->
                            "http://%s:%d/eureka"
                                    .formatted(
                                            discoveryServiceContainer.getHost(),
                                            discoveryServiceContainer.getMappedPort(8761)));
        };
    }

    public static void main(String[] args) {
        SpringApplication.from(PortfolioServiceApplication::main)
                .with(TestPortfolioServiceApplication.class)
                .run(args);
    }
}
