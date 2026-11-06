package com.trading.stock_simulator.web;

import com.trading.stock_simulator.asset.AssetService;
import com.trading.stock_simulator.asset.dto.AssetDto;
import com.trading.stock_simulator.price_tick.PriceTickService;
import com.trading.stock_simulator.price_tick.dto.PriceTickDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {
    private final AssetService assetService;
    private final PriceTickService priceTickService;

    public AssetController(AssetService assetService, PriceTickService priceTickService) {
        this.assetService = assetService;
        this.priceTickService = priceTickService;
    }

    @GetMapping
    public ResponseEntity<List<AssetDto>> getAllAssets() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    @GetMapping("/{assetId}/prices")
    public ResponseEntity<List<PriceTickDto>> getPriceHistory(@PathVariable Long assetId, @RequestParam(defaultValue = "60") int minutes) {
        return ResponseEntity.ok(priceTickService.getPriceHistory(assetId, minutes));
    }
}
