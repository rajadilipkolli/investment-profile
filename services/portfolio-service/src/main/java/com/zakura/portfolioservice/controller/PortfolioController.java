package com.zakura.portfolioservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zakura.portfolioservice.aspect.LogMethodInvocationAndParams;
import com.zakura.portfolioservice.exception.RecordNotFoundException;
import com.zakura.portfolioservice.models.Investment;
import com.zakura.portfolioservice.repository.PortfolioRepository;
import com.zakura.portfolioservice.util.Constants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/investments")
@RequiredArgsConstructor
public class PortfolioController {

	private final PortfolioRepository portfolioRepository;

	@LogMethodInvocationAndParams
	@GetMapping("/all/{userId}")
	public List<Investment> getAllInvestments(@PathVariable("userId") String userId) {
		return portfolioRepository.findByUserName(userId).orElse(new ArrayList<>());
	}

	@LogMethodInvocationAndParams
	@GetMapping("/profit/{userId}")
	public List<Investment> getProfitInvestments(@PathVariable("userId") String userId) {
		ArrayList<Investment> investments = portfolioRepository.findByUserName(userId).orElse(new ArrayList<>());
		return investments.stream().filter(Investment::isProfit).collect(Collectors.toList());
	}

	@LogMethodInvocationAndParams
	@GetMapping("/loss/{userId}")
	public List<Investment> getLossInvestments(@PathVariable("userId") String userId) {
		List<Investment> investments = portfolioRepository.findByUserName(userId).orElse(new ArrayList<>());
		return investments.stream().filter(p -> !p.isProfit()).collect(Collectors.toList());
	}

	@LogMethodInvocationAndParams
	@PostMapping("/add/{userId}")
	public @Valid Investment addUserInvestment(@RequestBody @Valid Investment investmentToSave,
			@PathVariable("userId") String userId) {
		Investment investment = portfolioRepository
				.findByUserNameAndNameAndType(userId, investmentToSave.getName(), investmentToSave.getType())
				.orElse(Investment.builder().build());
		if (null != investment.getName()) {
			int newQuantity = investment.getQuantity() + investmentToSave.getQuantity();
			portfolioRepository.deleteByUserNameAndNameAndType(userId, investment.getName(), investment.getType());
			investmentToSave.setQuantity(newQuantity);
		}
		investmentToSave.setUserName(userId);
		return portfolioRepository.save(investmentToSave);
	}

	@LogMethodInvocationAndParams
	@PostMapping("/update/{userId}")
	public @Valid Investment updateUserInvestment(@RequestBody @Valid Investment investmentToUpdate,
			@PathVariable("userId") String userId) {
		investmentToUpdate.setUserName(userId);
		Investment investment = portfolioRepository
				.findByUserNameAndNameAndType(userId, investmentToUpdate.getName(), investmentToUpdate.getType())
				.orElse(Investment.builder().build());
		if (null != investment.getName()) {
			portfolioRepository.deleteByUserNameAndNameAndType(userId, investment.getName(), investment.getType());
			return portfolioRepository.save(investmentToUpdate);
		} else {
			throw new RecordNotFoundException("Portfolio to update");
		}
	}

	@LogMethodInvocationAndParams
	@PostMapping("/delete/{userId}")
	public @Valid String deleteUserInvestment(@RequestBody @Valid Investment investmentToUpdate,
			@PathVariable("userId") String userId) {
		Investment investment = portfolioRepository
				.findByUserNameAndNameAndType(userId, investmentToUpdate.getName(), investmentToUpdate.getType())
				.orElse(Investment.builder().build());
		if (null != investment.getName()) {
			int statusInt = portfolioRepository.deleteByUserNameAndNameAndType(userId, investment.getName(),
					investment.getType());
			return 1 == statusInt ? Constants.SUCCESS : Constants.FAILED;
		} else {
			throw new RecordNotFoundException("Portfolio to delete");
		}
	}
}
