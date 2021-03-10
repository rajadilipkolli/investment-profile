package com.example.apigateway.controllers;

import com.example.apigateway.aspect.LogMethodInvocation;
import com.example.apigateway.client.PortfolioServiceClient;
import com.example.apigateway.models.investment.Investment;
import com.example.apigateway.security.jwt.JwtUtils;
import com.example.apigateway.util.Constants;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restservices")
public class PortfolioController {

    private final PortfolioServiceClient portfolioServiceClient;
    private final JwtUtils jwtUtils;

    private List<Investment> fallback(String authorizationToken) {
        return new ArrayList<>();
    }

    private Investment fallbackUpdate(String authorizationToken, Investment investment) {
        return Investment.builder().build();
    }

    private String fallbackDelete(String authorizationToken, Investment investment) {
        return Constants.FAILED_FALLBACK;
    }

    @LogMethodInvocation
    @GetMapping("/investments/all")
    // @CrossOrigin
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Investment> allInvestments(
            @RequestHeader("Authorization") String authorizationToken) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
        return portfolioServiceClient.getAllInvestments(userName);
    }

    @LogMethodInvocation
    @PostMapping("/investments/update")
    // @CrossOrigin
    @HystrixCommand(fallbackMethod = "fallbackUpdate")
    public @Valid Investment updateInvestment(
            @RequestHeader("Authorization") String authorizationToken,
            @RequestBody @Valid Investment investment) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
        return portfolioServiceClient.updateUserInvestment(investment, userName);
    }

    @LogMethodInvocation
    @PostMapping("/investments/delete")
    // @CrossOrigin
    @HystrixCommand(fallbackMethod = "fallbackDelete")
    public @Valid String deleteInvestment(
            @RequestHeader("Authorization") String authorizationToken,
            @RequestBody @Valid Investment investment) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
        return portfolioServiceClient.deleteUserInvestment(investment, userName);
    }

    @LogMethodInvocation
    @GetMapping("/investments/profit")
    // @CrossOrigin
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Investment> profitInvestments(
            @RequestHeader("Authorization") String authorizationToken) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
        return portfolioServiceClient.getProfitInvestments(userName);
    }

    @LogMethodInvocation
    @GetMapping("/investments/loss")
    // @CrossOrigin
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Investment> lossInvestments(
            @RequestHeader("Authorization") String authorizationToken) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
        return portfolioServiceClient.getLossInvestments(userName);
    }
}
