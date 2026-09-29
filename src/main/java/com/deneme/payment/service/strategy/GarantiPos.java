package com.deneme.payment.service.strategy;

import java.math.BigDecimal;

public class GarantiPos implements PaymentMethod {

    @Override
    public boolean processPayment(BigDecimal amount) {
        System.out.println("Garanti Bankası API'sine bağlanılıyor...");
        System.out.println(amount + " TL kredi kartından çekildi.");
        return true;
    }

    @Override
    public boolean processRefund(BigDecimal amount) {
        System.out.println("Kredi kartina iade yapılıyor... Tutar: " + amount);
        return true;
    }
}