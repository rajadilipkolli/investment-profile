package com.sipservice.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class SipVO implements Serializable {

    private BigDecimal monthlyInvestment;
	private BigDecimal expectedRateOfInterest;
	private int investmentDuration;
	private BigDecimal investedAmount;
	private BigDecimal predictedReturn;
    
    public BigDecimal getMonthlyInvestment() {
        return monthlyInvestment;
    }
    public void setMonthlyInvestment(BigDecimal monthlyInvestment) {
        this.monthlyInvestment = monthlyInvestment;
    }
    public BigDecimal getExpectedRateOfInterest() {
        return expectedRateOfInterest;
    }
    public void setExpectedRateOfInterest(BigDecimal expectedRateOfInterest) {
        this.expectedRateOfInterest = expectedRateOfInterest;
    }
    public int getInvestmentDuration() {
        return investmentDuration;
    }
    public void setInvestmentDuration(int investmentDuration) {
        this.investmentDuration = investmentDuration;
    }
    public BigDecimal getInvestedAmount() {
        return investedAmount;
    }
    public void setInvestedAmount(BigDecimal investedAmount) {
        this.investedAmount = investedAmount;
    }
    public BigDecimal getPredictedReturn() {
        return predictedReturn;
    }
    public void setPredictedReturn(BigDecimal predictedReturn) {
        this.predictedReturn = predictedReturn;
    }
    
}
