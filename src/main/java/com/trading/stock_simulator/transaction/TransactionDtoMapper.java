package com.trading.stock_simulator.transaction;

import com.trading.stock_simulator.transaction.dto.TransactionDto;

public class TransactionDtoMapper {
    static TransactionDto toDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setSymbol(transaction.getAsset().getSymbol());
        transactionDto.setQuantity(transaction.getQuantity());
        transactionDto.setPrice(transaction.getPrice());
        transactionDto.setTotal(transaction.getQuantity().multiply(transaction.getPrice()));
        transactionDto.setCreatedAt(transaction.getCreated_at());
        return transactionDto;
    }
}
