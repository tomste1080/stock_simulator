package com.trading.stock_simulator.asset;

import com.trading.stock_simulator.asset.dto.AssetDto;

public class AssetDtoMapper {
    static AssetDto toDto(Asset asset) {
        AssetDto assetDto = new AssetDto();
        assetDto.setId(asset.getId());
        assetDto.setSymbol(asset.getSymbol());
        assetDto.setAssetType(asset.getAssetType());
        assetDto.setCurrentPrice(asset.getCurrentPrice());
        assetDto.setPriceUpdatedAt(asset.getPriceUpdatedAt());
        return assetDto;
    }
}
