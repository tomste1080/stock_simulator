package com.trading.stock_simulator.market;

import com.trading.stock_simulator.asset.Asset;
import com.trading.stock_simulator.asset.AssetDtoMapper;
import com.trading.stock_simulator.asset.AssetRepository;
import com.trading.stock_simulator.asset.dto.AssetDto;
import com.trading.stock_simulator.price_tick.PriceTick;
import com.trading.stock_simulator.price_tick.PriceTickRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PriceSimulator {
    private final AssetRepository assetRepository;
    private final PriceTickRepository priceTickRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public PriceSimulator(AssetRepository assetRepository,
                          PriceTickRepository priceTickRepository,
                          ApplicationEventPublisher applicationEventPublisher) {
        this.assetRepository = assetRepository;
        this.priceTickRepository = priceTickRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    @Scheduled(fixedRate = 10000)
    public void updatePrices() {
        List<Asset> assets = assetRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Asset asset: assets) {
            BigDecimal newCurrentPrice = calculateNewPrice(asset.getCurrentPrice());
            asset.setCurrentPrice(newCurrentPrice);
            asset.setPriceUpdatedAt(now);
            PriceTick priceTick = new PriceTick();
            priceTick.setAsset(asset);
            priceTick.setPrice(newCurrentPrice);
            priceTick.setTimestamp(now);
            priceTickRepository.save(priceTick);
        }
        List<AssetDto> updatedAssets = assets.stream()
                .map(AssetDtoMapper::toDto)
                .toList();
        applicationEventPublisher.publishEvent(new PricesUpdatedEvent(updatedAssets));
    }

    private BigDecimal calculateNewPrice(BigDecimal currentPrice) {
        double change = ThreadLocalRandom.current().nextDouble(-0.01, 0.01);
        BigDecimal changeRate = BigDecimal.valueOf(1 + change);
        return changeRate.multiply(currentPrice).setScale(4, RoundingMode.HALF_UP);
    }
}
