package com.zakura.stockservice.client;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zakura.stockservice.models.Investment;

@FeignClient("portfolio-service")
public interface PortfolioServiceClient {

	@PostMapping("/portfolio-service/investments/add/{userId}")
	@CrossOrigin
	Investment addUserInvestment(@RequestBody @Valid Investment investmentToSave,
								 @PathVariable("userId") String userId);
}
