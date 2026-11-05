package com.trading.stock_simulator.user;

import com.trading.stock_simulator.asset.Asset;
import com.trading.stock_simulator.holding.Holding;
import com.trading.stock_simulator.order.Order;
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
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Column(name = "cash_balance")
    private BigDecimal cashBalance;
    private Long version;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Holding> holdings = new ArrayList<>();
}
