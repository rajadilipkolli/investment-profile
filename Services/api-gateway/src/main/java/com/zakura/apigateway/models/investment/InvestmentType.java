package com.zakura.apigateway.models.investment;

public enum InvestmentType {

	STOCK("Stock"), MUTUAL_FUND("Mutual Fund"), FIXED_DEPOSIT("Fix Deposit");

	private String investment;

	private InvestmentType(String investment) {
		this.investment = investment;
	}

	public String getInvestment() {
		return this.investment;
	}
}
