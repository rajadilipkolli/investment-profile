package com.zakura.stockservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;

@TestConfiguration(proxyBeanMethods = false)
public class TestStockServiceApplication {

	static Network network = Network.newNetwork();

	@Bean
	@ServiceConnection
	MongoDBContainer mongoDbContainer() {
		return new MongoDBContainer("mongo:latest").withNetwork(network).withNetworkAliases("mongodb");
	}

	@Bean
	@ServiceConnection(name = "openzipkin/zipkin")
	GenericContainer<?> zipkinContainer() {
		return new GenericContainer<>("openzipkin/zipkin:latest").withExposedPorts(9411)
		.withNetwork(network)
        .withNetworkAliases("zipkin");
	}

	@Bean
	GenericContainer<?> discoveryServiceContainer(DynamicPropertyRegistry dynamicPropertyRegistry) {
		GenericContainer<?> discoveryServiceContainer = new GenericContainer<>("dockertmt/discovery-service")
				.withExposedPorts(8761).withNetwork(network).withNetworkAliases("discovery-service")
				.waitingFor(Wait.forHttp("/actuator/health"));
		dynamicPropertyRegistry.add("eureka.client.service-url.defaultZone", () -> "http://%s:%d/eureka".formatted(
                discoveryServiceContainer.getHost(), discoveryServiceContainer.getMappedPort(8761)));
		return discoveryServiceContainer;
	}

	public static void main(String[] args) {
		SpringApplication.from(StockServiceApplication::main).with(TestStockServiceApplication.class).run(args);
	}

}
