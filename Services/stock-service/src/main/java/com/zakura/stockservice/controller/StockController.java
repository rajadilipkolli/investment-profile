package com.zakura.stockservice.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zakura.stockservice.aspect.LogMethodInvocation;
import com.zakura.stockservice.aspect.LogMethodInvocationAndParams;
import com.zakura.stockservice.exception.RecordNotFoundException;
import com.zakura.stockservice.models.Stock;
import com.zakura.stockservice.models.StockRequest;
import com.zakura.stockservice.repository.StockRepository;
import com.zakura.stockservice.service.StockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StockController {

	private final StockRepository stockRepository;
	private final StockService stockService;

	@LogMethodInvocation
	@GetMapping("/view/all")
	public ArrayList<Stock> getAvailableStocks() {
		return stockRepository.findAll();
	}

	@LogMethodInvocationAndParams
	@PostMapping("/add/{userId}")
	public String saveUserStock(@RequestBody @Valid StockRequest stockRequest, @PathVariable("userId") String userId) {
		Optional<Stock> stockBoughtOptional = stockRepository.findByNameAndInvestmentType(stockRequest.getName(),
				stockRequest.getInvestmentType());
		if (!stockBoughtOptional.isPresent()) {
			log.error("Stock details not found");
			throw new RecordNotFoundException("Stock detail");
		} else {
			return stockService.saveUserStock(stockRequest, userId);
		}
	}

}
