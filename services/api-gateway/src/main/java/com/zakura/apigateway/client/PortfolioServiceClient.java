package com.zakura.apigateway.client;

import com.zakura.apigateway.models.investment.Investment;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("portfolio-service")
public interface PortfolioServiceClient {

    @GetMapping("/portfolio-service/investments/all/{userId}")
    @CrossOrigin
    ArrayList<Investment> getAllInvestments(@PathVariable("userId") String userId);

    @GetMapping("/portfolio-service/investments/profit/{userId}")
    @CrossOrigin
    ArrayList<Investment> getProfitInvestments(@PathVariable("userId") String userId);

    @GetMapping("/portfolio-service/investments/loss/{userId}")
    @CrossOrigin
    ArrayList<Investment> getLossInvestments(@PathVariable("userId") String userId);

    @PostMapping("/portfolio-service/investments/update/{userId}")
    @CrossOrigin
    @Valid
    Investment updateUserInvestment(
            @RequestBody @Valid Investment investmentToUpdate,
            @PathVariable("userId") String userId);

    @PostMapping("/portfolio-service/investments/delete/{userId}")
    @CrossOrigin
    @Valid
    String deleteUserInvestment(
            @RequestBody @Valid Investment investmentToDelete,
            @PathVariable("userId") String userId);
}
