package com.example.apigateway.controllers;

import com.example.apigateway.aspect.LogMethodInvocationAndParams;
import com.example.apigateway.client.StockServiceClient;
import com.example.apigateway.models.investment.Stock;
import com.example.apigateway.security.jwt.JwtUtils;
import com.example.apigateway.util.Constants;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
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
public class StockController {

    private final StockServiceClient stockServiceClient;
    private final JwtUtils jwtUtils;

    private ArrayList<Stock> fallback(String authorizationToken) {
        return new ArrayList<>();
    }

    private String buyStockfallback(String authorizationToken, Stock stock) {
        return Constants.FAILED_FALLBACK;
    }

    @LogMethodInvocationAndParams
    @GetMapping("/availableStocks")
    // @CrossOrigin
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Stock> availableStocksToBuy(
            @RequestHeader("Authorization") String authorizationToken) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
        log.info("userName : {}", userName);
        return stockServiceClient.getAvailableStocks();
    }

    @LogMethodInvocationAndParams
    @PostMapping("/buy/stock")
    // @CrossOrigin
    @HystrixCommand(fallbackMethod = "buyStockfallback")
    public @Valid String buyStock(
            @RequestHeader("Authorization") String authorizationToken, @RequestBody Stock stock) {
        String jwtToken = authorizationToken.substring(7);
        String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
        return stockServiceClient.saveUserStock(stock, userName);
    }
}
