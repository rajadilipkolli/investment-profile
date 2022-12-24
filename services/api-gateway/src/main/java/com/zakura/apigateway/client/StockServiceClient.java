/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.client;

import com.zakura.apigateway.models.investment.Stock;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@HttpExchange("http://localhost:8083/")
public interface StockServiceClient {

    @GetExchange("/stock-service/view/all")
    @CrossOrigin
    Flux<Stock> getAvailableStocks();

    @PostExchange("/stock-service/add/{userId}")
    @CrossOrigin
    Mono<String> saveUserStock(
            @RequestBody @Valid Stock stockRequest, @PathVariable("userId") String userId);
}
