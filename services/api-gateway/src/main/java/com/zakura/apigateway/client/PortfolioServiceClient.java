/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.client;

import com.zakura.apigateway.models.investment.Investment;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

// @FeignClient("portfolio-service")
@HttpExchange("http://localhost:8082/")
public interface PortfolioServiceClient {

    @GetExchange("/portfolio-service/investments/all/{userId}")
    @CrossOrigin
    List<Investment> getAllInvestments(@PathVariable("userId") String userId);

    @GetExchange("/portfolio-service/investments/profit/{userId}")
    @CrossOrigin
    ArrayList<Investment> getProfitInvestments(@PathVariable("userId") String userId);

    @GetExchange("/portfolio-service/investments/loss/{userId}")
    @CrossOrigin
    ArrayList<Investment> getLossInvestments(@PathVariable("userId") String userId);

    @PostExchange("/portfolio-service/investments/update/{userId}")
    @CrossOrigin
    @Valid
    Investment updateUserInvestment(
            @RequestBody @Valid Investment investmentToUpdate,
            @PathVariable("userId") String userId);

    @PostExchange("/portfolio-service/investments/delete/{userId}")
    @CrossOrigin
    @Valid
    String deleteUserInvestment(
            @RequestBody @Valid Investment investmentToDelete,
            @PathVariable("userId") String userId);
}
