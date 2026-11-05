package com.trading.stock_simulator.holding;

import com.trading.stock_simulator.asset.Asset;
import com.trading.stock_simulator.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "holdings")
@Getter
@Setter
@NoArgsConstructor
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal quantity;
    @Column(name = "average_buy_price")
    private BigDecimal averageBuyPrice;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "assets_id")
    private Asset asset;
}
