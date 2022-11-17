package com.zakura.stockservice;

import com.zakura.stockservice.models.Stock;
import com.zakura.stockservice.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.math.BigDecimal;

@EnableFeignClients
@SpringBootApplication
public class StockServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
	}

	@Autowired
	StockRepository stockRepository;


	@Override
	public void run(String... args) {
		if (this.stockRepository.findByName("Birla").isEmpty()){
			Stock stock = new Stock();
			stock.setName("Birla");
			stock.setInvestmentType("Stock");
			stock.setCurrentPrice(BigDecimal.valueOf(145));
			stock.setAnticipatedGrowth(15);
			stock.setTerm(2);
			this.stockRepository.save(stock);
		}
	}
}
