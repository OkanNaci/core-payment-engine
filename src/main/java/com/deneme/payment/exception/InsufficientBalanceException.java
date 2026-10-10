package com.deneme.payment.exception;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException() {
        super("Insufficient wallet balance.");
    }
}