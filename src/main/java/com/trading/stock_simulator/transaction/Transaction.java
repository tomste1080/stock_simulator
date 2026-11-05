package com.trading.stock_simulator.transaction;

import com.trading.stock_simulator.asset.Asset;
import com.trading.stock_simulator.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType transactionType;
    private BigDecimal quantity;
    private BigDecimal price;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "assets_id")
    private Asset asset;
}
