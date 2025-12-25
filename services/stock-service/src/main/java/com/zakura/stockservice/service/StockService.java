/* Licensed under Apache-2.0 2025 */
package com.zakura.stockservice.service;

import com.zakura.stockservice.aspect.LogMethodInvocation;
import com.zakura.stockservice.aspect.LogProcessTime;
import com.zakura.stockservice.client.PortfolioServiceClient;
import com.zakura.stockservice.models.Investment;
import com.zakura.stockservice.models.Stock;
import com.zakura.stockservice.models.StockRequest;
import com.zakura.stockservice.repository.StockRepository;
import com.zakura.stockservice.util.Constants;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockService {

    private final PortfolioServiceClient portfolioServiceClient;
    private final StockRepository stockRepository;

    private final SecureRandom random = new SecureRandom();

    public StockService(
            PortfolioServiceClient portfolioServiceClient, StockRepository stockRepository) {
        this.portfolioServiceClient = portfolioServiceClient;
        this.stockRepository = stockRepository;
    }

    @LogMethodInvocation
    @LogProcessTime
    public String saveUserStock(StockRequest stockRequest, String userId) {
        float randomFloat = random.nextFloat() * 100;
        float profitLossPercent = (float) (Math.round(randomFloat * 100.0) / 100.0);

        BigDecimal currentPrice =
                stockRequest
                        .currentPrice()
                        .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(profitLossPercent))
                        .add(stockRequest.currentPrice());
        currentPrice = currentPrice.setScale(2, RoundingMode.HALF_UP);
        boolean isProfit = true;
        Investment investmentToSave =
                Investment.builder()
                        .costPrice(stockRequest.currentPrice())
                        .currentPrice(currentPrice)
                        .name(stockRequest.name())
                        .quantity(stockRequest.quantity())
                        .type(stockRequest.investmentType())
                        .profitLossPercent(profitLossPercent)
                        .profit(isProfit)
                        .build();
        Investment investmentPortfolio =
                portfolioServiceClient.addUserInvestment(investmentToSave, userId);
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

    public Optional<Stock> findByStockName(String name) {
        return this.stockRepository.findByName(name);
    }

    public Stock saveStock(StockRequest stockRequest) {
        return this.stockRepository.save(
                Stock.builder()
                        .anticipatedGrowth(stockRequest.anticipatedGrowth())
                        .currentPrice(stockRequest.currentPrice())
                        .term(stockRequest.term())
                        .name(stockRequest.name())
                        .investmentType(stockRequest.investmentType())
                        .build());
    }
}
