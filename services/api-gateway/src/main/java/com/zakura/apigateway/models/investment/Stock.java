/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.models.investment;

import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
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
public class Stock implements Serializable {

    @Serial private static final long serialVersionUID = -3965849301639516052L;

    @NotBlank private String name;

    private String investmentType;

    private BigDecimal currentPrice;

    private float anticipatedGrowth;

    private int term;

    private int quantity;
}
