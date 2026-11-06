package com.trading.stock_simulator.holding.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class HoldingDto {
    private Long id;
    private BigDecimal quantity;
    private BigDecimal averageBuyPrice;
    private Long assetId;
    private BigDecimal currentPrice;
    private BigDecimal currentValue;
    private BigDecimal profitLoss;
}
