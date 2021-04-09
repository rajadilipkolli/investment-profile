package com.zakura.apigateway.models.investment;

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
public class Investment implements Serializable {
	private static final long serialVersionUID = 3372752921153392127L;

	private String name;
	private String type;
	private int quantity;
	private BigDecimal costPrice;
	private BigDecimal currentPrice;
	private float profitLossPercent;
	private boolean profit;

}
