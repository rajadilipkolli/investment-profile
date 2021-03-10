package com.zakura.apigateway.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zakura.apigateway.aspect.LogMethodInvocationAndParams;
import com.zakura.apigateway.client.StockServiceClient;
import com.zakura.apigateway.models.investment.Stock;
import com.zakura.apigateway.security.jwt.JwtUtils;
import com.zakura.apigateway.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	public ArrayList<Stock> availableStocksToBuy(@RequestHeader("Authorization") String authorizationToken) {
		String jwtToken = authorizationToken.substring(7);
		String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
		log.info("userName : ", userName);
		return stockServiceClient.getAvailableStocks();
	}

	@LogMethodInvocationAndParams
	@PostMapping("/buy/stock")
	// @CrossOrigin
	@HystrixCommand(fallbackMethod = "buyStockfallback")
	public @Valid String buyStock(@RequestHeader("Authorization") String authorizationToken, @RequestBody Stock stock) {
		String jwtToken = authorizationToken.substring(7);
		String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
		return stockServiceClient.saveUserStock(stock, userName);
	}
}
