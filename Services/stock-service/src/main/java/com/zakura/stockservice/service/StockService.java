package com.zakura.stockservice.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.zakura.stockservice.models.Stock;
import com.zakura.stockservice.repository.StockRepository;
import org.springframework.stereotype.Component;

import com.zakura.stockservice.aspect.LogMethodInvocation;
import com.zakura.stockservice.aspect.LogProcessTime;
import com.zakura.stockservice.client.PortfolioServiceClient;
import com.zakura.stockservice.models.Investment;
import com.zakura.stockservice.models.StockRequest;
import com.zakura.stockservice.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockService {

	private final PortfolioServiceClient portfolioServiceClient;
	private final StockRepository stockRepository;

	/**
	 * Method to save stock for a user(Buy Stock)
	 * 
	 * @param stockRequest
	 * @param userId
	 * @return
	 */
	@LogMethodInvocation
	@LogProcessTime
	public String saveUserStock(StockRequest stockRequest, String userId) {
		Random rd = new Random();
		float randomFloat = rd.nextFloat() * 100;
		float profitLossPercent = (float) (Math.round(randomFloat * 100.0) / 100.0);

		BigDecimal currentPrice = stockRequest.getCurrentPrice().divide(BigDecimal.valueOf(100))
				.multiply(BigDecimal.valueOf(profitLossPercent)).add(stockRequest.getCurrentPrice());
		currentPrice = currentPrice.setScale(2, RoundingMode.HALF_UP);
		boolean isProfit = true;
		Investment investmentToSave = Investment.builder().costPrice(stockRequest.getCurrentPrice())
				.currentPrice(currentPrice).name(stockRequest.getName()).quantity(stockRequest.getQuantity())
				.type(stockRequest.getInvestmentType()).profitLossPercent(profitLossPercent).profit(isProfit).build();
		Investment investmentPortfolio = portfolioServiceClient.addUserInvestment(investmentToSave, userId);
		if (null != investmentPortfolio.getName()) {
			log.info("User transaction to buy stock is complete!");
			return Constants.TRANSACTION_SUCCESFUL;
		} else {
			log.info("User transaction to buy stock is incomplete!");
			return null;
		}
	}

    public List<Stock> findAll() {
		return this.stockRepository.findAll();
    }

	public Optional<Stock> findByNameAndInvestmentType(String name, String investmentType) {
		return this.stockRepository.findByNameAndInvestmentType(name, investmentType);
	}
}
