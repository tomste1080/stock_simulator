package com.trading.stock_simulator.exception;

public class NotEnoughAssetException extends RuntimeException {
    public NotEnoughAssetException(String message) {
        super(message);
    }
}
