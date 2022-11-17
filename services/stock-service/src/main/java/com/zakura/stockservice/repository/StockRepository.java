package com.zakura.stockservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zakura.stockservice.aspect.LogProcessTime;
import com.zakura.stockservice.models.Stock;

public interface StockRepository extends MongoRepository<Stock, String> {

	@LogProcessTime
	Optional<Stock> findByName(String name);

	@LogProcessTime
	Optional<Stock> findByNameAndInvestmentType(String name, String investmentType);

	@LogProcessTime
	List<Stock> findAll();
}
