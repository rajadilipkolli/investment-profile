package com.zakura.stockservice.models;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class StockRequest implements Serializable {

	private static final long serialVersionUID = 2223858521883494244L;

	@JsonIgnore
	private String userName;

	@NotBlank
	private String name;

	private String investmentType;

	private BigDecimal currentPrice;

	private float anticipatedGrowth;

	private int term;

	private int quantity;

}
