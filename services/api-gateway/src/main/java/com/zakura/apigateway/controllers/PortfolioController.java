/* Licensed under Apache-2.0 2022 */
package com.zakura.apigateway.controllers;

import com.zakura.apigateway.aspect.LogMethodInvocation;
import com.zakura.apigateway.client.PortfolioServiceClient;
import com.zakura.apigateway.models.investment.Investment;
import com.zakura.apigateway.security.jwt.JwtTokenProvider;
import com.zakura.apigateway.util.Constants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restservices")
@Slf4j
public class PortfolioController {

    private final PortfolioServiceClient portfolioServiceClient;
    private final JwtTokenProvider jwtTokenProvider;

    private List<Investment> fallback(String authorizationToken, Exception ex) {
        log.error("Exception :{}", ex.getMessage());
        log.info("Serving from FallBack");
        return new ArrayList<>();
    }

    private Investment fallbackUpdate(
            String authorizationToken, Investment investment, Exception ex) {
        log.error("Exception :{}", ex.getMessage());
        return Investment.builder().build();
    }

    private String fallbackDelete(String authorizationToken, Investment investment, Exception ex) {
        log.error("Exception :{}", ex.getMessage());
        return Constants.FAILED_FALLBACK;
    }

    @LogMethodInvocation
    @GetMapping("/investments/all")
    // @CrossOrigin
    @CircuitBreaker(name = "default", fallbackMethod = "fallback")
    public List<Investment> allInvestments(
            @RequestHeader("Authorization") String authorizationToken) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtTokenProvider.getUsernameFromToken(jwtToken);
        return portfolioServiceClient.getAllInvestments(userName);
    }

    @LogMethodInvocation
    @PostMapping("/investments/update")
    // @CrossOrigin
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackUpdate")
    public @Valid Investment updateInvestment(
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
    public @Valid String deleteInvestment(
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
    public ArrayList<Investment> profitInvestments(
            @RequestHeader("Authorization") String authorizationToken) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtTokenProvider.getUsernameFromToken(jwtToken);
        return portfolioServiceClient.getProfitInvestments(userName);
    }

    @LogMethodInvocation
    @GetMapping("/investments/loss")
    // @CrossOrigin
    @CircuitBreaker(name = "default", fallbackMethod = "fallback")
    public ArrayList<Investment> lossInvestments(
            @RequestHeader("Authorization") String authorizationToken) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtTokenProvider.getUsernameFromToken(jwtToken);
        return portfolioServiceClient.getLossInvestments(userName);
    }
}
