/* Licensed under Apache-2.0 2025 */
package com.zakura.stockservice;

import java.time.Duration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.grafana.LgtmStackContainer;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestStockServiceApplication {

    static Network network = Network.newNetwork();

    @Bean
    @ServiceConnection
    MongoDBContainer mongoDbContainer() {
        return new MongoDBContainer("mongo:latest")
                .withNetwork(network)
                .withNetworkAliases("mongodb");
    }

    @Bean
    @ServiceConnection
    LgtmStackContainer lgtmContainer() {
        return new LgtmStackContainer(DockerImageName.parse("grafana/otel-lgtm:0.13.0"))
                .withStartupTimeout(Duration.ofMinutes(2))
                .withNetwork(network)
                .withNetworkAliases("grafana-lgtm");
    }

    @Bean
    GenericContainer<?> discoveryServiceContainer() {
        return new GenericContainer<>("dockertmt/discovery-service")
                .withExposedPorts(8761)
                .withNetwork(network)
                .withNetworkAliases("discovery-service")
                .waitingFor(Wait.forHttp("/actuator/health"));
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
        SpringApplication.from(StockServiceApplication::main)
                .with(TestStockServiceApplication.class)
                .run(args);
    }
}
