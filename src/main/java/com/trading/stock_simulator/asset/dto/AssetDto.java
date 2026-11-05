package com.trading.stock_simulator.asset.dto;

import com.trading.stock_simulator.asset.AssetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AssetDto {
    private Long id;
    private String symbol;
    private String name;
    private AssetType assetType;
    private BigDecimal currentPrice;
    private LocalDateTime priceUpdatedAt;
}
