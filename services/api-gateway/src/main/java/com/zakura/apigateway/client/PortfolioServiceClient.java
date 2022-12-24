/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.client;

import com.zakura.apigateway.models.investment.Investment;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// @FeignClient("portfolio-service")
@HttpExchange("http://localhost:8082/api-gateway")
public interface PortfolioServiceClient {

    @GetExchange("/portfolio-service/investments/all/{userId}")
    @CrossOrigin
    Flux<Investment> getAllInvestments(@PathVariable("userId") String userId);

    @GetExchange("/portfolio-service/investments/profit/{userId}")
    @CrossOrigin
    Flux<Investment> getProfitInvestments(@PathVariable("userId") String userId);

    @GetExchange("/portfolio-service/investments/loss/{userId}")
    @CrossOrigin
    Flux<Investment> getLossInvestments(@PathVariable("userId") String userId);

    @PostExchange("/portfolio-service/investments/update/{userId}")
    @CrossOrigin
    @Valid
    Mono<Investment> updateUserInvestment(
            @RequestBody @Valid Investment investmentToUpdate,
            @PathVariable("userId") String userId);

    @PostExchange("/portfolio-service/investments/delete/{userId}")
    @CrossOrigin
    @Valid
    Mono<String> deleteUserInvestment(
            @RequestBody @Valid Investment investmentToDelete,
            @PathVariable("userId") String userId);
}
