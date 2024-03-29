package com.zakura.stockservice.controller;

import com.zakura.stockservice.aspect.LogMethodInvocation;
import com.zakura.stockservice.aspect.LogMethodInvocationAndParams;
import com.zakura.stockservice.exception.RecordNotFoundException;
import com.zakura.stockservice.exception.StockExistsException;
import com.zakura.stockservice.models.Stock;
import com.zakura.stockservice.models.StockRequest;
import com.zakura.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StockController {

	private final StockService stockService;

	@LogMethodInvocation
	@GetMapping("/view/all")
	public List<Stock> getAvailableStocks() {
		return stockService.findAll();
	}

	@LogMethodInvocationAndParams
	@PostMapping("/add/{userId}")
	public String saveUserStock(@RequestBody @Valid StockRequest stockRequest, @PathVariable("userId") String userId) {
		Optional<Stock> stockBoughtOptional = stockService.findByNameAndInvestmentType(stockRequest.name(),
				stockRequest.investmentType());
		if (stockBoughtOptional.isEmpty()) {
			log.error("Stock details not found");
			throw new RecordNotFoundException("Stock detail");
		} else {
			return stockService.saveUserStock(stockRequest, userId);
		}
	}

	@LogMethodInvocationAndParams
	@PostMapping("/stock")
	public Stock addNewStock(@RequestBody @Valid StockRequest stockRequest) {
		Optional<Stock> byStockName = stockService.findByStockName(stockRequest.name());
		if (byStockName.isPresent()) {
			throw new StockExistsException(stockRequest.name());
		} else {
			return stockService.saveStock(stockRequest);
		}
	}

}
