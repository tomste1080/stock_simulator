package com.trading.stock_simulator.trade;

import com.trading.stock_simulator.asset.Asset;
import com.trading.stock_simulator.asset.AssetRepository;
import com.trading.stock_simulator.exception.NotEnoughAssetException;
import com.trading.stock_simulator.exception.NotEnoughMoneyException;
import com.trading.stock_simulator.exception.ResourceNotFoundException;
import com.trading.stock_simulator.holding.Holding;
import com.trading.stock_simulator.holding.HoldingRepository;
import com.trading.stock_simulator.trade.dto.TradeDto;
import com.trading.stock_simulator.transaction.Transaction;
import com.trading.stock_simulator.transaction.TransactionDtoMapper;
import com.trading.stock_simulator.transaction.TransactionRepository;
import com.trading.stock_simulator.transaction.TransactionType;
import com.trading.stock_simulator.transaction.dto.TransactionDto;
import com.trading.stock_simulator.user.User;
import com.trading.stock_simulator.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TradeService {
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final HoldingRepository holdingRepository;
    private final TransactionRepository transactionRepository;

    public TradeService(UserRepository userRepository,
                        AssetRepository assetRepository,
                        HoldingRepository holdingRepository,
                        TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
        this.holdingRepository = holdingRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionDto buy(Long userId, TradeDto tradeDto) {
        User user = findUser(userId);
        Asset asset = findAsset(tradeDto.getAssetId());
        BigDecimal totalPrice = tradeDto.getQuantity().multiply(asset.getCurrentPrice());
        if (user.getCashBalance().compareTo(totalPrice) < 0) {
            throw new NotEnoughMoneyException("There are insufficient funds in the account");
        }
        user.setCashBalance(user.getCashBalance().subtract(totalPrice));
        addToHolding(tradeDto, user, totalPrice, asset);
        Transaction transaction = saveTransaction(user, asset, tradeDto.getQuantity(), asset.getCurrentPrice(), TransactionType.BUY);
        return TransactionDtoMapper.toDto(transaction);
    }

    private BigDecimal calculateAveragePrice(Holding holding, TradeDto tradeDto, BigDecimal totalPrice) {
        BigDecimal oldCost = holding.getQuantity().multiply(holding.getAverageBuyPrice());
        BigDecimal newQuantity = holding.getQuantity().add(tradeDto.getQuantity());
        return oldCost.add(totalPrice).divide(newQuantity, 4, RoundingMode.HALF_UP);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));
    }

    private Asset findAsset(Long assetId) {
        return assetRepository.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset with ID: " + assetId + " not found"));
    }

    private Transaction saveTransaction(User user, Asset asset, BigDecimal quantity, BigDecimal price, TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUser(user);
        transaction.setAsset(asset);
        transactionRepository.save(transaction);
        return transaction;
    }

    private void addToHolding(TradeDto tradeDto, User user, BigDecimal totalPrice, Asset asset) {
        Optional<Holding> existingHolding = holdingRepository.findByUser_IdAndAsset_Id(user.getId(), asset.getId());
        if (existingHolding.isPresent()) {
            Holding holding = existingHolding.get();
            holding.setAverageBuyPrice(calculateAveragePrice(holding, tradeDto, totalPrice));
            holding.setQuantity(holding.getQuantity().add(tradeDto.getQuantity()));
        } else {
            Holding holding = new Holding();
            holding.setQuantity(tradeDto.getQuantity());
            holding.setAverageBuyPrice(asset.getCurrentPrice());
            holding.setUser(user);
            holding.setAsset(asset);
            holdingRepository.save(holding);
        }
    }

    @Transactional
    public TransactionDto sell(Long userId, TradeDto tradeDto) {
        User user = findUser(userId);
        Asset asset = findAsset(tradeDto.getAssetId());
        removeFromHolding(user, asset, tradeDto);
        BigDecimal income = tradeDto.getQuantity().multiply(asset.getCurrentPrice());
        user.setCashBalance(income.add(user.getCashBalance()));
        Transaction transaction = saveTransaction(user, asset, tradeDto.getQuantity(), asset.getCurrentPrice(), TransactionType.SELL);
        return TransactionDtoMapper.toDto(transaction);
    }

    private void removeFromHolding(User user, Asset asset, TradeDto tradeDto) {
        Holding holding = holdingRepository.findByUser_IdAndAsset_Id(user.getId(), asset.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Holding with user ID " + user.getId() + " not found"));
        BigDecimal remaining = holding.getQuantity().subtract(tradeDto.getQuantity());
        if (remaining.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughAssetException("You do not have enough assets in holding");
        }
        if (remaining.compareTo(BigDecimal.ZERO) == 0) {
            holdingRepository.delete(holding);
        } else {
            holding.setQuantity(remaining);
        }
    }
}
