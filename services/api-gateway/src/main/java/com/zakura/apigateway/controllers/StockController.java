/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.controllers;

import com.zakura.apigateway.aspect.LogMethodInvocationAndParams;
import com.zakura.apigateway.client.StockServiceClient;
import com.zakura.apigateway.models.investment.Stock;
import com.zakura.apigateway.security.jwt.JwtTokenProvider;
import com.zakura.apigateway.util.Constants;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restservices")
@Slf4j
public class StockController {

    private final StockServiceClient stockServiceClient;
    private final JwtTokenProvider jwtTokenProvider;

    private Flux<Stock> fallback(String authorizationToken, Exception ex) {
        log.error("Exception :{}", ex.getMessage());
        return Flux.empty();
    }

    private Mono<String> buyStockfallback(String authorizationToken, Stock stock, Exception ex) {
        log.error("Exception :{}", ex.getMessage());
        return Mono.just(Constants.FAILED_FALLBACK);
    }

    @LogMethodInvocationAndParams
    @GetMapping("/availableStocks")
    // @CrossOrigin
    @CircuitBreaker(name = "default", fallbackMethod = "fallback")
    public Flux<Stock> availableStocksToBuy(
            @RequestHeader("Authorization") String authorizationToken) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtTokenProvider.getUsernameFromToken(jwtToken);
        log.info("userName : {}", userName);
        return stockServiceClient.getAvailableStocks();
    }

    @LogMethodInvocationAndParams
    @PostMapping("/buy/stock")
    // @CrossOrigin
    @CircuitBreaker(name = "default", fallbackMethod = "buyStockfallback")
    public @Valid Mono<String> buyStock(
            @RequestHeader("Authorization") String authorizationToken, @RequestBody Stock stock) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtTokenProvider.getUsernameFromToken(jwtToken);
        return stockServiceClient.saveUserStock(stock, userName);
    }
}
