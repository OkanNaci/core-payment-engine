package com.deneme.payment;

public interface PaymentMethod {
    boolean processPayment(double amount);


    boolean processRefund(double amount);
}