package com.acme.ecommerce.service;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {
        super("Not enough stock.");
    }
}
