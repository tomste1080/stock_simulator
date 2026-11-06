package com.trading.stock_simulator.web;

import com.trading.stock_simulator.trade.TradeService;
import com.trading.stock_simulator.trade.dto.TradeDto;
import com.trading.stock_simulator.transaction.dto.TransactionDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/trades")
public class TradeController {
    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping("/buy")
    public ResponseEntity<TransactionDto> buy(@Valid @RequestBody TradeDto tradeDto, @PathVariable Long userId) {
        return ResponseEntity.ok(tradeService.buy(userId, tradeDto));
    }

    @PostMapping("/sell")
    public ResponseEntity<TransactionDto> sell(@Valid @RequestBody TradeDto tradeDto, @PathVariable Long userId) {
        return ResponseEntity.ok(tradeService.sell(userId, tradeDto));
    }
}
