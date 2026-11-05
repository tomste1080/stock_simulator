package com.trading.stock_simulator.price_tick;

import org.springframework.stereotype.Service;

@Service
public class PriceTickService {
    private final PriceTickService priceTickService;

    public PriceTickService(PriceTickService priceTickService) {
        this.priceTickService = priceTickService;
    }
}
