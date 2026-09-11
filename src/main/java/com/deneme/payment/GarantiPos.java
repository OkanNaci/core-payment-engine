package com.deneme.payment;


public class GarantiPos implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Garanti Bankası API'sine bağlanılıyor...");
        System.out.println(amount + " TL kredi kartından çekildi.");
        return true;
    }
}