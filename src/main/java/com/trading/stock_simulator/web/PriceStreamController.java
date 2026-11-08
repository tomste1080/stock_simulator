package com.trading.stock_simulator.web;

import com.trading.stock_simulator.market.PriceStreamService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/prices")
public class PriceStreamController {
    private final PriceStreamService priceStreamService;

    public PriceStreamController(PriceStreamService priceStreamService) {
        this.priceStreamService = priceStreamService;
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamPrices() {
        return priceStreamService.subscribe();
    }
}
