package com.trading.stock_simulator.transaction.dto;

import com.trading.stock_simulator.transaction.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private String symbol;
    private TransactionType transactionType;
    private BigDecimal quantity;
    private BigDecimal price;
    BigDecimal total;
    LocalDateTime createdAt;
}
