package com.trading.stock_simulator.asset;

import org.springframework.stereotype.Service;

@Service
public class AssetService {
    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }
}
