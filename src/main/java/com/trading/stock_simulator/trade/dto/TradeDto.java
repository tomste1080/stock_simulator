package com.trading.stock_simulator.trade.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TradeDto {
    private Long assetId;
    @Positive
    private BigDecimal quantity;
}
