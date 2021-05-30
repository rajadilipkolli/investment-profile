package com.zakura.apigateway.models.investment;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
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

    private static final long serialVersionUID = -3965849301639516052L;

    @NotBlank private String name;

    private String investmentType;

    private BigDecimal currentPrice;

    private float anticipatedGrowth;

    private int term;

    private int quantity;
}
