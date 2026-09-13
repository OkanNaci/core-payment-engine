package com.deneme.payment.exception;


public class NoRefundPendingException extends RuntimeException {

    public NoRefundPendingException(String message) {
        super(message);
    }
}