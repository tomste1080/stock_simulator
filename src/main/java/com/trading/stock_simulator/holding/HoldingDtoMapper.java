package com.trading.stock_simulator.holding;

import com.trading.stock_simulator.asset.Asset;
import com.trading.stock_simulator.holding.dto.HoldingDto;

import java.math.BigDecimal;

public class HoldingDtoMapper {
    static HoldingDto toDto(Holding holding) {
        Asset asset = holding.getAsset();
        HoldingDto holdingDto = new HoldingDto();
        holdingDto.setId(holding.getId());
        holdingDto.setSymbol(asset.getSymbol());
        holdingDto.setQuantity(holding.getQuantity());
        holdingDto.setAverageBuyPrice(holding.getAverageBuyPrice());
        holdingDto.setAssetId(holding.getAsset().getId());
        holdingDto.setCurrentPrice(asset.getCurrentPrice());
        BigDecimal currentValue = asset.getCurrentPrice().multiply(holding.getQuantity());
        holdingDto.setCurrentValue(currentValue);
        BigDecimal profitLoss = (asset.getCurrentPrice().subtract(holding.getAverageBuyPrice())).multiply(holding.getQuantity());
        holdingDto.setProfitLoss(profitLoss);
        return holdingDto;
    }
}
