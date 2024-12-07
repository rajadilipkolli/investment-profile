package com.example.sipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.GenericContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestSipServiceApplication {

    @Bean
    @ServiceConnection(name = "openzipkin/zipkin")
    GenericContainer<?> zipkinContainer() {
        return new GenericContainer<>("openzipkin/zipkin:latest").withExposedPorts(9411);
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
        SpringApplication.from(SipServiceApplication::main)
                .with(TestSipServiceApplication.class)
                .run(args);
    }
}
