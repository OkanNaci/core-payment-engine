package com.deneme.payment.service.strategy;

import java.math.BigDecimal;

public class CryptoWallet implements PaymentMethod {

    @Override
    public boolean processPayment(BigDecimal amount) {
        System.out.println("Blockchain ağına bağlanılıyor...");
        System.out.println(amount + " USDT cüzdandan transfer edildi.");
        return true;
    }

    @Override
    public boolean processRefund(BigDecimal amount) {
        System.out.println("Kripto cüzdanına iade yapılıyor... Tutar: " + amount);
        return true;
    }
}