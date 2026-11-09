package com.trading.stock_simulator.order;

import com.trading.stock_simulator.asset.Asset;
import com.trading.stock_simulator.asset.AssetRepository;
import com.trading.stock_simulator.exception.InvalidOrderStateException;
import com.trading.stock_simulator.exception.NotEnoughAssetException;
import com.trading.stock_simulator.exception.NotEnoughMoneyException;
import com.trading.stock_simulator.exception.ResourceNotFoundException;
import com.trading.stock_simulator.holding.Holding;
import com.trading.stock_simulator.holding.HoldingRepository;
import com.trading.stock_simulator.order.dto.CreateOrderDto;
import com.trading.stock_simulator.order.dto.OrderDto;
import com.trading.stock_simulator.user.User;
import com.trading.stock_simulator.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final HoldingRepository holdingRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        AssetRepository assetRepository,
                        HoldingRepository holdingRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
        this.holdingRepository = holdingRepository;
    }

    @Transactional
    public OrderDto createOrder(Long userId, CreateOrderDto dto) {
        User user = getUser(userId);
        Asset asset = getAsset(dto.getAssetId());
        switch (dto.getOrderType()) {
            case STOP_LOSS, TAKE_PROFIT -> validateSellOrder(dto, user);
            case LIMIT_BUY -> checkCashBalance(user, dto);
        }
        Order orderToSave = createOrderToSave(dto, user, asset);
        orderRepository.save(orderToSave);
        return OrderDtoMapper.toDto(orderToSave);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));
    }

    private Asset getAsset(Long assetId) {
        return assetRepository.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset with ID: " + assetId + " not found"));
    }

    private Holding getHolding(CreateOrderDto createOrderDto, User user) {
        return holdingRepository.findByUser_IdAndAsset_Id(user.getId(), createOrderDto.getAssetId())
                .orElseThrow(() -> new NotEnoughAssetException("Does not have this asset"));
    }

    private void validateSellOrder(CreateOrderDto createOrderDto, User user) {
        Holding holding = getHolding(createOrderDto, user);
        BigDecimal remaining = holding.getQuantity().subtract(createOrderDto.getQuantity());
        if (remaining.compareTo(BigDecimal.ZERO) < 0)
            throw new NotEnoughAssetException("You do not have enough assets");
    }

    private void checkCashBalance(User user, CreateOrderDto dto) {
        BigDecimal cashBalance = user.getCashBalance();
        BigDecimal totalOrderCost = dto.getQuantity().multiply(dto.getTriggerPrice());
        BigDecimal remaining = cashBalance.subtract(totalOrderCost);
        if (remaining.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughMoneyException("You do not have enough cash balance to execute order");
        }
    }

    private Order createOrderToSave(CreateOrderDto dto, User user, Asset asset) {
        Order order = OrderDtoMapper.toEntity(dto);
        order.setOrderStatus(OrderStatus.ACTIVE);
        order.setCreatedAt(LocalDateTime.now());
        order.setUser(user);
        order.setAsset(asset);
        return order;
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByUser(Long userId) {
        return orderRepository.findOrdersByUser_IdOrderByCreatedAtDesc(userId)
                .stream()
                .map(OrderDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public OrderDto cancelOrder(Long userId, Long orderId) {
        Order order = orderRepository.findByIdAndUser_Id(orderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with ID: " + orderId + " not found"));
        if (order.getOrderStatus() != (OrderStatus.ACTIVE)) {
            throw new InvalidOrderStateException("Only active orders can be cancelled");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return OrderDtoMapper.toDto(order);
    }
}
