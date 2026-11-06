package com.trading.stock_simulator.transaction;

import com.trading.stock_simulator.transaction.dto.TransactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public List<TransactionDto> getUserTransactions(Long userId) {
        return transactionRepository.findByUser_Id(userId)
                .stream()
                .map(TransactionDtoMapper::toDto)
                .toList();
    }
}
