package com.trading.stock_simulator.web;

import com.trading.stock_simulator.holding.HoldingService;
import com.trading.stock_simulator.holding.dto.HoldingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/holdings")
public class HoldingController {
    private final HoldingService holdingService;

    public HoldingController(HoldingService holdingService) {
        this.holdingService = holdingService;
    }

    @GetMapping
    public ResponseEntity<List<HoldingDto>> getHoldings(@PathVariable Long userId) {
        return ResponseEntity.ok(holdingService.getUserHoldings(userId));
    }
}
