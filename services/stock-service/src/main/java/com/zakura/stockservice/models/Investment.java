/* Licensed under Apache-2.0 2025 */
package com.zakura.stockservice.models;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Investment implements Serializable {

    @Serial private static final long serialVersionUID = 8585508434149256126L;
    private String name;
    private String type;
    private int quantity;
    private BigDecimal costPrice;
    private BigDecimal currentPrice;
    private float profitLossPercent;
    private boolean profit;
}
