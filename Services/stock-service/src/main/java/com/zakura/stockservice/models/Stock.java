package com.zakura.stockservice.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "stocks")
public class Stock implements Serializable {

	private static final long serialVersionUID = 2223858521883494244L;

	@JsonIgnore
	private String userName;

	@NotBlank
	private String name;

	private String investmentType;

	private BigDecimal currentPrice;

	private float anticipatedGrowth;

	private int term;

}
