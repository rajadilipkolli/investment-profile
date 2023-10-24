package com.example.sipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestSipServiceApplication {

	@Bean
	@ServiceConnection(name = "openzipkin/zipkin")
	GenericContainer<?> zipkinContainer() {
		return new GenericContainer<>("openzipkin/zipkin:latest").withExposedPorts(9411);
	}

	@Bean
	GenericContainer<?> discoveryServiceContainer(DynamicPropertyRegistry dynamicPropertyRegistry) {
		GenericContainer<?> discoveryServiceContainer = new GenericContainer<>("dockertmt/discovery-service")
				.withExposedPorts(8761);
		dynamicPropertyRegistry.add("eureka.client.service-url.defaultZone", () -> String.format("http://%s:%d/eureka",
				discoveryServiceContainer.getHost(), discoveryServiceContainer.getMappedPort(8761)));
		return discoveryServiceContainer;
	}

	public static void main(String[] args) {
		SpringApplication.from(SipServiceApplication::main).with(TestSipServiceApplication.class).run(args);
	}

}