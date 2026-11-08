package com.trading.stock_simulator.market;

import com.trading.stock_simulator.asset.dto.AssetDto;

import java.util.List;

public record PricesUpdatedEvent(List<AssetDto> assets) {
}
