package com.trading.stock_simulator.order.dto;


import com.trading.stock_simulator.order.OrderStatus;
import com.trading.stock_simulator.order.OrderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String symbol;
    private OrderType orderType;
    private OrderStatus orderStatus;
    private BigDecimal triggerPrice;
    private BigDecimal quantity;
    private LocalDateTime createdAt;
    private LocalDateTime executedAt;
}
