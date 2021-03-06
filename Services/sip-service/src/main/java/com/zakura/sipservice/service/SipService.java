package com.zakura.sipservice.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import com.zakura.sipservice.aspect.LogMethodInvocation;
import com.zakura.sipservice.aspect.LogProcessTime;
import com.zakura.sipservice.models.SipVO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SipService {

	@LogProcessTime
	@LogMethodInvocation
	public BigDecimal calculateSIP(SipVO sipVO) {
		BigDecimal periodicInterestRate = sipVO.getExpectedRateOfInterest()
				.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP)
				.divide(BigDecimal.valueOf(12), 6, RoundingMode.HALF_UP);

		int numberOfPayments = sipVO.getInvestmentDuration() * 12;

		BigDecimal estimatedReturns = sipVO.getMonthlyInvestment()
				.multiply((((BigDecimal.valueOf(1).add(periodicInterestRate)).pow(numberOfPayments))
						.subtract(BigDecimal.valueOf(1))).divide(periodicInterestRate, 2, RoundingMode.HALF_UP))
				.multiply(BigDecimal.valueOf(1).add(periodicInterestRate));

		return estimatedReturns.setScale(2, RoundingMode.HALF_UP);
	}
}
