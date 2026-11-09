package com.trading.stock_simulator.order.dto;

import com.trading.stock_simulator.order.OrderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderDto {
    @NotNull
    private Long assetId;
    @NotNull
    private OrderType orderType;
    @NotNull
    @Positive
    private BigDecimal triggerPrice;
    @NotNull
    @Positive
    private BigDecimal quantity;
}
