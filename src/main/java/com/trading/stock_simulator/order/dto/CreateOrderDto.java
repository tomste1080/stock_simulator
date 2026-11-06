package com.trading.stock_simulator.order.dto;

import com.trading.stock_simulator.order.OrderType;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateOrderDto {
    private Long assetId;
    private OrderType orderType;
    @Positive
    private BigDecimal triggerPrice;
    @Positive
    private BigDecimal quantity;
}
