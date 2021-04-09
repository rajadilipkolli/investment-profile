package com.zakura.stockservice.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@EnableCircuitBreaker
@EnableDiscoveryClient
@Configuration
public class SpringConfig {
}
