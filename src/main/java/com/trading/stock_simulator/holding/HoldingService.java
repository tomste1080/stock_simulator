package com.trading.stock_simulator.holding;

import com.trading.stock_simulator.holding.dto.HoldingDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoldingService {
    private final HoldingRepository holdingRepository;

    public HoldingService(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }

    @Transactional(readOnly = true)
    public List<HoldingDto> getUserHoldings(Long userId) {
        return holdingRepository.findByUser_Id(userId)
                .stream()
                .map(HoldingDtoMapper::toDto)
                .toList();
    }
}
