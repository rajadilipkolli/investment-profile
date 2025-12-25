/* Licensed under Apache-2.0 2025 */
package com.zakura.stockservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

public record StockRequest(
        @JsonIgnore String userName,
        @NotBlank(message = "StockName cant be Blank") String name,
        String investmentType,
        BigDecimal currentPrice,
        float anticipatedGrowth,
        int term,
        int quantity)
        implements Serializable {}
