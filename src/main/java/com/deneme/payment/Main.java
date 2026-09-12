package com.deneme.payment;

public class Main {
    public static void main(String[] args) {
        Order myOrder = new Order("MAIN-001");

        myOrder.addProduct(new Product("Apple MacBook Pro", 75000.0));
        myOrder.addProduct(new Product("Logitech MX Master 3", 3500.0));

        System.out.println("Sipariş ID: " + "MAIN-001");
        System.out.println("Ödenecek Toplam Tutar: " + myOrder.getTotalAmount() + " TL");

        PaymentMethod pos = new GarantiPos();

        if (pos.processPayment(myOrder.getTotalAmount())) {
            myOrder.markAsPaid();
            System.out.println("Ödeme Durumu: Başarılı!");
        } else {
            System.out.println("Ödeme Durumu: Başarısız!");
        }
    }
}