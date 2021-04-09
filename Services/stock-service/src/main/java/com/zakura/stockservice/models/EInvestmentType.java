package com.zakura.stockservice.models;

public enum EInvestmentType {

	STOCK("Stock"), MUTUAL_FUND("Mutual Fund"), FIXED_DEPOSIT("Fix Deposit");

	private String investment;

	EInvestmentType(String investment) {
		this.investment = investment;
	}

	public String getInvestment() {
		return this.investment;
	}
}
