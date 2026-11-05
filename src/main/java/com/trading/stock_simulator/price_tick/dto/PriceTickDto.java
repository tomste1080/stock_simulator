package com.trading.stock_simulator.price_tick.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PriceTickDto {
    private BigDecimal price;
    private LocalDateTime timestamp;
}
