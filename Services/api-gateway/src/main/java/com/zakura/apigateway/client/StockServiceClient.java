package com.zakura.apigateway.client;

import com.zakura.apigateway.models.investment.Stock;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("stock-service")
public interface StockServiceClient {

    @GetMapping("/stock-service/view/all")
    @CrossOrigin
    ArrayList<Stock> getAvailableStocks();

    @PostMapping("/stock-service/add/{userId}")
    @CrossOrigin
    String saveUserStock(
            @RequestBody @Valid Stock stockRequest, @PathVariable("userId") String userId);
}
