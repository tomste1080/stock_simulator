package com.trading.stock_simulator.asset;

import com.trading.stock_simulator.holding.Holding;
import com.trading.stock_simulator.order.Order;
import com.trading.stock_simulator.price_tick.PriceTick;
import com.trading.stock_simulator.transaction.Transaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assets")
@Getter
@Setter
@NoArgsConstructor
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AssetType assetType;
    @Column(name = "current_price")
    private BigDecimal currentPrice;
    @Column(name = "price_updated_at")
    private LocalDateTime priceUpdatedAt;
    @OneToMany(mappedBy = "asset")
    private List<Holding> holdings = new ArrayList<>();
    @OneToMany(mappedBy = "asset")
    private List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "asset")
    private List<Transaction> transactions = new ArrayList<>();
    @OneToMany(mappedBy = "asset")
    private List<PriceTick> priceTicks = new ArrayList<>();
}
