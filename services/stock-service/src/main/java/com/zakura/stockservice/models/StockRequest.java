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

public record StockRequest(@JsonIgnore String userName,
                           @NotBlank(message = "StockName cant be Blank")
                           String name,
                           String investmentType,
                           BigDecimal currentPrice,
                           float anticipatedGrowth,
                           int term,
                           int quantity) implements Serializable {

}
