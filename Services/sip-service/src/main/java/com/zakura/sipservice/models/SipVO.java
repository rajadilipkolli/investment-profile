package com.zakura.sipservice.models;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SipVO implements Serializable{

	private static final long serialVersionUID = 3890471029731222377L;
	private BigDecimal monthlyInvestment;
	private BigDecimal expectedRateOfInterest;
	private int investmentDuration;
	private BigDecimal investedAmount;
	private BigDecimal predictedReturn;
	
}
