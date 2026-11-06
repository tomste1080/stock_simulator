package com.trading.stock_simulator.market;

import com.trading.stock_simulator.asset.Asset;
import com.trading.stock_simulator.asset.AssetRepository;
import com.trading.stock_simulator.price_tick.PriceTick;
import com.trading.stock_simulator.price_tick.PriceTickRepository;
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

    public PriceSimulator(AssetRepository assetRepository, PriceTickRepository priceTickRepository) {
        this.assetRepository = assetRepository;
        this.priceTickRepository = priceTickRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 5000000)
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
    }

    private BigDecimal calculateNewPrice(BigDecimal currentPrice) {
        double change = ThreadLocalRandom.current().nextDouble(-0.01, 0.01);
        BigDecimal changeRate = BigDecimal.valueOf(1 + change);
        return changeRate.multiply(currentPrice).setScale(4, RoundingMode.HALF_UP);
    }
}
