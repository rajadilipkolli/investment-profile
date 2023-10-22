package com.zakura.stockservice.models;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "stocks")
@NoArgsConstructor
@AllArgsConstructor
public class Stock implements Serializable {

	@Serial
	private static final long serialVersionUID = 2223858521883494244L;

	@JsonIgnore
	private String userName;

	@NotBlank
	@Indexed(unique = true)
	private String name;

	private String investmentType;

	private BigDecimal currentPrice;

	private float anticipatedGrowth;

	private int term;

}
