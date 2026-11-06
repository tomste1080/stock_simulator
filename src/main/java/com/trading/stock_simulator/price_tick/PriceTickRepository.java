package com.trading.stock_simulator.price_tick;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceTickRepository extends JpaRepository<PriceTick, Long> {

    List<PriceTick> findByAsset_IdAndTimestampAfterOrderByTimestampAsc(Long assetId, LocalDateTime timestampAfter);
}
