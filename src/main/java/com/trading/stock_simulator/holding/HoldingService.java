package com.trading.stock_simulator.holding;

import org.springframework.stereotype.Service;

@Service
public class HoldingService {
    private final HoldingRepository holdingRepository;

    public HoldingService(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }
}
