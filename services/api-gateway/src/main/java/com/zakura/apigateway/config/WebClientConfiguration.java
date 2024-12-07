/* Licensed under Apache-2.0 2022 */
package com.zakura.apigateway.config;

import com.zakura.apigateway.client.PortfolioServiceClient;
import com.zakura.apigateway.client.StockServiceClient;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration(proxyBeanMethods = false)
public class WebClientConfiguration {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    HttpServiceProxyFactory httpServiceProxyFactory(WebClient webClient) {
        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClient);
        // default value is 5 sec, as we are unable to get data increasing timeout
        webClientAdapter.setBlockTimeout(Duration.ofSeconds(20));
        return HttpServiceProxyFactory.builderFor(webClientAdapter).build();
    }

    @Bean
    public PortfolioServiceClient portfolioServiceClient(
            HttpServiceProxyFactory httpServiceProxyFactory) {
        return httpServiceProxyFactory.createClient(PortfolioServiceClient.class);
    }

    @Bean
    public StockServiceClient stockServiceClient(HttpServiceProxyFactory httpServiceProxyFactory) {
        return httpServiceProxyFactory.createClient(StockServiceClient.class);
    }
}
