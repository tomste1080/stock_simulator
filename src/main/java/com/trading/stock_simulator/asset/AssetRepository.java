package com.trading.stock_simulator.asset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    @Override
    List<Asset> findAll();
    
    boolean existsById(Long id);
}
