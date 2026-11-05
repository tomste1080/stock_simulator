package com.trading.stock_simulator.price_tick;

import com.trading.stock_simulator.price_tick.dto.PriceTickDto;

public class PriceTickDtoMapper {
    static PriceTickDto toDto(PriceTick priceTick) {
        PriceTickDto priceTickDto = new PriceTickDto();
        priceTickDto.setPrice(priceTick.getPrice());
        priceTickDto.setTimestamp(priceTick.getTimestamp());
        return priceTickDto;
    }
}
