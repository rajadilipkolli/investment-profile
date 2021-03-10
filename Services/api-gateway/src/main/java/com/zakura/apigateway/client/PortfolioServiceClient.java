package com.zakura.apigateway.client;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zakura.apigateway.models.investment.Investment;

@FeignClient("portfolio-service")
public interface PortfolioServiceClient {

	@GetMapping("/portfolio-service/investments/all/{userId}")
	@CrossOrigin
	public ArrayList<Investment> getAllInvestments(@PathVariable("userId") String userId);

	@GetMapping("/portfolio-service/investments/profit/{userId}")
	@CrossOrigin
	public ArrayList<Investment> getProfitInvestments(@PathVariable("userId") String userId);

	@GetMapping("/portfolio-service/investments/loss/{userId}")
	@CrossOrigin
	public ArrayList<Investment> getLossInvestments(@PathVariable("userId") String userId);

	@PostMapping("/portfolio-service/investments/update/{userId}")
	@CrossOrigin
	public @Valid Investment updateUserInvestment(@RequestBody @Valid Investment investmentToUpdate,
			@PathVariable("userId") String userId);

	@PostMapping("/portfolio-service/investments/delete/{userId}")
	@CrossOrigin
	public @Valid String deleteUserInvestment(@RequestBody @Valid Investment investmentToDelete,
			@PathVariable("userId") String userId);
}
