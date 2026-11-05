package com.trading.stock_simulator.price_tick;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceTickRepository extends JpaRepository<PriceTick, Long> {
}
