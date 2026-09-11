package com.deneme.payment;

public class CryptoWallet implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Blockchain ağına bağlanılıyor...");
        System.out.println(amount + " USDT cüzdandan transfer edildi.");
        return true;
    }
}