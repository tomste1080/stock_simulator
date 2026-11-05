package com.trading.stock_simulator.order;

import com.trading.stock_simulator.asset.Asset;
import com.trading.stock_simulator.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OrderType orderType;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;
    @Column(name = "trigger_price")
    private BigDecimal triggerPrice;
    private BigDecimal quantity;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "executed_at")
    private LocalDateTime executedAt;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "assets_id")
    private Asset asset;
}
