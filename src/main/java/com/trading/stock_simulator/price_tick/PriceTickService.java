package com.trading.stock_simulator.price_tick;

import org.springframework.stereotype.Service;

@Service
public class PriceTickService {
    private final PriceTickRepository priceTickRepository;

    public PriceTickService(PriceTickRepository priceTickRepository) {
        this.priceTickRepository = priceTickRepository;
    }
}
