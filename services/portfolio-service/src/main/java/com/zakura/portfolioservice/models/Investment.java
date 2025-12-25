package com.zakura.portfolioservice.models;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "investments")
public class Investment implements Serializable {
    @Serial private static final long serialVersionUID = 2223858521883494244L;

	private String userName;

	private String name;
	private String type;
	private int quantity;
	private BigDecimal costPrice;
	private BigDecimal currentPrice;
	private float profitLossPercent;
	private boolean profit;

}
