/* Licensed under Apache-2.0 2025 */
package com.zakura.stockservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "stocks")
@NoArgsConstructor
@AllArgsConstructor
public class Stock implements Serializable {

    @Serial private static final long serialVersionUID = 2223858521883494244L;

    @JsonIgnore private String userName;

    @NotBlank
    @Indexed(unique = true)
    private String name;

    private String investmentType;

    private BigDecimal currentPrice;

    private float anticipatedGrowth;

    private int term;
}
