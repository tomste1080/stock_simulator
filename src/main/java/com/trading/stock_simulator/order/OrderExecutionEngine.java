package com.trading.stock_simulator.order;

import com.trading.stock_simulator.asset.dto.AssetDto;
import com.trading.stock_simulator.market.PricesUpdatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
    import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderExecutionEngine {
    private final OrderRepository orderRepository;
    private final OrderExecutor orderExecutor;

    public OrderExecutionEngine(OrderRepository orderRepository, OrderExecutor orderExecutor) {
        this.orderRepository = orderRepository;
        this.orderExecutor = orderExecutor;
    }

    @TransactionalEventListener
    public void onPricesUpdated(PricesUpdatedEvent event) {
        Map<Long, BigDecimal> pricesMap = event.assets()
                .stream()
                .collect(Collectors.toMap(AssetDto::getId, AssetDto::getCurrentPrice));
        List<Order> activeOrders = orderRepository.findByOrderStatus(OrderStatus.ACTIVE);
        for (Order order: activeOrders) {
            BigDecimal price = pricesMap.get(order.getAsset().getId());
            if (shouldExecute(order, price)) {
                try {
                    orderExecutor.execute(order.getId());
                } catch (RuntimeException e) {
                    orderExecutor.markFailed(order.getId());
                }
            }
        }
    }

    private boolean shouldExecute(Order order, BigDecimal price) {
        return switch (order.getOrderType()) {
            case STOP_LOSS, LIMIT_BUY -> price.compareTo(order.getTriggerPrice()) <= 0;
            case TAKE_PROFIT -> price.compareTo(order.getTriggerPrice()) >= 0;
        };
    }
}
