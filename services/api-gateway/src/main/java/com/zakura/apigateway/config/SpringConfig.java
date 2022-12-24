/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableDiscoveryClient
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class SpringConfig implements WebFluxConfigurer {

    private final ApplicationProperties properties;

    /**
     * Config to enable CORS(Cross-origin-resource sharing) So that the service can be discovered by
     * localhost
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // registry.addMapping("/**").allowedOrigins("http://localhost:4200");
        registry.addMapping(properties.getCors().getPathPattern())
                .allowedMethods(properties.getCors().getAllowedMethods())
                .allowedHeaders(properties.getCors().getAllowedHeaders())
                .allowedOriginPatterns(properties.getCors().getAllowedOriginPatterns())
                .allowCredentials(properties.getCors().isAllowCredentials());
    }
}
