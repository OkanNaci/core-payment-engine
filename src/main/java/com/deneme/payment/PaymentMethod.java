package com.deneme.payment;

// KAVRAMLAR: Abstraction, Interface
public interface PaymentMethod {
    // Sadece metodun imzası var, gövdesi yok.
    boolean processPayment(double amount);
}