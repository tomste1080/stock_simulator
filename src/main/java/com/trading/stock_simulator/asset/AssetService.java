package com.trading.stock_simulator.asset;

import com.trading.stock_simulator.asset.dto.AssetDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetService {
    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Transactional(readOnly = true)
    public List<AssetDto> getAllAssets() {
        return assetRepository.findAll()
                .stream()
                .map(AssetDtoMapper::toDto)
                .toList();
    }
}
