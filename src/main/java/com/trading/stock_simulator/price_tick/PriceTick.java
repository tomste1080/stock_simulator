package com.trading.stock_simulator.price_tick;

import com.trading.stock_simulator.asset.Asset;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_ticks")
@Getter
@Setter
@NoArgsConstructor
public class PriceTick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "assets_id")
    private Asset asset;
}
