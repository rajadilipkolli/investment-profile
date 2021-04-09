package com.zakura.apigateway.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableZuulProxy
@Configuration
public class SpringConfig implements WebMvcConfigurer {

	/**
	 * Config to enable CORS(Cross-origin-resource sharing) So that the service can
	 * be discovered by localhost
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:4200");
	}
}
