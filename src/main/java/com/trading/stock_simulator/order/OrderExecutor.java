package com.trading.stock_simulator.order;

import com.trading.stock_simulator.exception.ResourceNotFoundException;
import com.trading.stock_simulator.trade.TradeService;
import com.trading.stock_simulator.trade.dto.TradeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderExecutor {
    private final OrderRepository orderRepository;
    private final TradeService tradeService;

    public OrderExecutor(OrderRepository orderRepository, TradeService tradeService) {
        this.orderRepository = orderRepository;
        this.tradeService = tradeService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute(Long orderId) {
        Order order = getOrder(orderId);
        if (order.getOrderStatus() != OrderStatus.ACTIVE) {
            return;
        }
        TradeDto tradeDto = createTradeDto(order.getAsset().getId(), order.getQuantity());
        executeTrade(order.getOrderType(), order.getUser().getId(), tradeDto);
        order.setExecutedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.EXECUTED);
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with ID: " + orderId + " not found"));
    }

    private TradeDto createTradeDto(Long assetId, BigDecimal quantity) {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setAssetId(assetId);
        tradeDto.setQuantity(quantity);
        return tradeDto;
    }

    private void executeTrade(OrderType orderType, Long userId, TradeDto tradeDto) {
        switch (orderType) {
            case STOP_LOSS, TAKE_PROFIT -> tradeService.sell(userId, tradeDto);
            case LIMIT_BUY -> tradeService.buy(userId, tradeDto);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markFailed(Long orderId) {
        Order order = getOrder(orderId);
        if (order.getOrderStatus() == OrderStatus.ACTIVE) {
            order.setOrderStatus(OrderStatus.FAILED);
        }
    }
}
