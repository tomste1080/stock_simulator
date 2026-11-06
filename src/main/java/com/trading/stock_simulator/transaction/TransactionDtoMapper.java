package com.trading.stock_simulator.transaction;

import com.trading.stock_simulator.transaction.dto.TransactionDto;

public class TransactionDtoMapper {
    public static TransactionDto toDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setSymbol(transaction.getAsset().getSymbol());
        transactionDto.setQuantity(transaction.getQuantity());
        transactionDto.setPrice(transaction.getPrice());
        transactionDto.setTotal(transaction.getQuantity().multiply(transaction.getPrice()));
        transactionDto.setCreatedAt(transaction.getCreatedAt());
        transactionDto.setTransactionType(transaction.getTransactionType());
        return transactionDto;
    }
}
