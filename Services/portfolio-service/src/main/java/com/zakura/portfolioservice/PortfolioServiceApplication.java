package com.zakura.portfolioservice;

import com.zakura.portfolioservice.models.Investment;
import com.zakura.portfolioservice.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.math.BigDecimal;

@EnableDiscoveryClient
@SpringBootApplication
public class PortfolioServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioServiceApplication.class, args);
	}

	@Autowired
	PortfolioRepository portfolioRepository;

	@Override
	public void run(String... args) {
		if (this.portfolioRepository.findByUserName("test@mail.com").isEmpty()) {
			Investment portfolio = new Investment();
			portfolio.setUserName("test@mail.com");
			portfolio.setName("Sensex");
			portfolio.setType("Stock");
			portfolio.setQuantity(100);
			portfolio.setCostPrice(BigDecimal.valueOf(500));
			portfolio.setCurrentPrice(BigDecimal.valueOf(625));
			portfolio.setProfitLossPercent(25);
			portfolio.setProfit(true);
			this.portfolioRepository.save(portfolio);
		}
	}
}
