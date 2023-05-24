/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.controllers;

import com.zakura.apigateway.aspect.LogMethodInvocation;
import com.zakura.apigateway.client.PortfolioServiceClient;
import com.zakura.apigateway.models.investment.Investment;
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
public class PortfolioController {

    private final PortfolioServiceClient portfolioServiceClient;
    private final JwtTokenProvider jwtTokenProvider;

    private Flux<Investment> fallback(String authorizationToken, Exception ex) {
        log.error("Exception :{}", ex.getMessage());
        log.info("Serving from FallBack");
        return Flux.empty();
    }

    private Mono<Investment> fallbackUpdate(
            String authorizationToken, Investment investment, Exception ex) {
        log.error("Exception :{}", ex.getMessage());
        return Mono.just(Investment.builder().build());
    }

    private Mono<String> fallbackDelete(
            String authorizationToken, Investment investment, Exception ex) {
        log.error("Exception :{}", ex.getMessage());
        return Mono.just(Constants.FAILED_FALLBACK);
    }

    @LogMethodInvocation
    @GetMapping("/investments/all")
    // @CrossOrigin
    @CircuitBreaker(name = "default", fallbackMethod = "fallback")
    public Flux<Investment> allInvestments(
            @RequestHeader("Authorization") String authorizationToken) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtTokenProvider.getUsernameFromToken(jwtToken);
        return portfolioServiceClient.getAllInvestments(userName);
    }

    @LogMethodInvocation
    @PostMapping("/investments/update")
    // @CrossOrigin
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackUpdate")
    public @Valid Mono<Investment> updateInvestment(
            @RequestHeader("Authorization") String authorizationToken,
            @RequestBody @Valid Investment investment) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtTokenProvider.getUsernameFromToken(jwtToken);
        return portfolioServiceClient.updateUserInvestment(investment, userName);
    }

    @LogMethodInvocation
    @PostMapping("/investments/delete")
    // @CrossOrigin
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackDelete")
    public @Valid Mono<String> deleteInvestment(
            @RequestHeader("Authorization") String authorizationToken,
            @RequestBody @Valid Investment investment) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtTokenProvider.getUsernameFromToken(jwtToken);
        return portfolioServiceClient.deleteUserInvestment(investment, userName);
    }

    @LogMethodInvocation
    @GetMapping("/investments/profit")
    // @CrossOrigin
    @CircuitBreaker(name = "default", fallbackMethod = "fallback")
    public Flux<Investment> profitInvestments(
            @RequestHeader("Authorization") String authorizationToken) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtTokenProvider.getUsernameFromToken(jwtToken);
        return portfolioServiceClient.getProfitInvestments(userName);
    }

    @LogMethodInvocation
    @GetMapping("/investments/loss")
    // @CrossOrigin
    @CircuitBreaker(name = "default", fallbackMethod = "fallback")
    public Flux<Investment> lossInvestments(
            @RequestHeader("Authorization") String authorizationToken) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtTokenProvider.getUsernameFromToken(jwtToken);
        return portfolioServiceClient.getLossInvestments(userName);
    }
}
