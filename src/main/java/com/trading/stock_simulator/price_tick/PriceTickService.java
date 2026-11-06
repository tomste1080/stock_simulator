package com.trading.stock_simulator.price_tick;

import com.trading.stock_simulator.asset.Asset;
import com.trading.stock_simulator.asset.AssetRepository;
import com.trading.stock_simulator.exception.ResourceNotFoundException;
import com.trading.stock_simulator.price_tick.dto.PriceTickDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PriceTickService {
    private final PriceTickRepository priceTickRepository;
    private final AssetRepository assetRepository;

    public PriceTickService(PriceTickRepository priceTickRepository, AssetRepository assetRepository) {
        this.priceTickRepository = priceTickRepository;
        this.assetRepository = assetRepository;
    }

    @Transactional(readOnly = true)
    public List<PriceTickDto> getPriceHistory(Long assetId, int minutes) {
        if (!assetRepository.existsById(assetId)) {
            throw new ResourceNotFoundException("Asset with ID: " + assetId + " not found");
        }
        LocalDateTime timestampAfter = LocalDateTime.now().minusMinutes(minutes);
        return priceTickRepository.findByAsset_IdAndTimestampAfterOrderByTimestampAsc(assetId, timestampAfter)
                .stream()
                .map(PriceTickDtoMapper::toDto)
                .toList();
    }
}
