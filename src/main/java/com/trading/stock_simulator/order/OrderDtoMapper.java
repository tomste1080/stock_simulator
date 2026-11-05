package com.trading.stock_simulator.order;

import com.trading.stock_simulator.order.dto.OrderDto;

public class OrderDtoMapper {
    static OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setSymbol(order.getAsset().getSymbol());
        orderDto.setOrderType(order.getOrderType());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setTriggerPrice(order.getTriggerPrice());
        orderDto.setQuantity(order.getQuantity());
        orderDto.setCreatedAt(order.getCreatedAt());
        orderDto.setExecutedAt(order.getExecutedAt());
        return orderDto;
    }
}
