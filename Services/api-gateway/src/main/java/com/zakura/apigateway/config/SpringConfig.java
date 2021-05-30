package com.zakura.apigateway.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableDiscoveryClient
@Configuration(proxyBeanMethods = false)
public class SpringConfig implements WebFluxConfigurer {

    /**
     * Config to enable CORS(Cross-origin-resource sharing) So that the service can be discovered by
     * localhost
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200");
    }
}
