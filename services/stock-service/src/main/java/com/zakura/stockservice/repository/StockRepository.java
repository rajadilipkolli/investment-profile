/* Licensed under Apache-2.0 2025 */
package com.zakura.stockservice.repository;

import com.zakura.stockservice.aspect.LogProcessTime;
import com.zakura.stockservice.models.Stock;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockRepository extends MongoRepository<Stock, String> {

    @LogProcessTime
    Optional<Stock> findByName(String name);

    @LogProcessTime
    Optional<Stock> findByNameAndInvestmentType(String name, String investmentType);

    @LogProcessTime
    List<Stock> findAll();
}
