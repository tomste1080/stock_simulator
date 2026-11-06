package com.trading.stock_simulator.holding;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HoldingRepository extends JpaRepository<Holding, Long> {

    Optional<Holding> findByUser_IdAndAsset_Id(Long userId, Long assetId);

    List<Holding> findByUser_Id(Long userId);
}
