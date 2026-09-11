package com.deneme.payment;

public class Main {
    public static void main(String[] args) {

        Order[] pendingOrders = new Order[3];
        pendingOrders[0] = new Order("ORD-001", 1500.50);
        pendingOrders[1] = new Order("ORD-002", -50.0); // Hatalı veri denemesi
        pendingOrders[2] = new Order("ORD-003", 45000.0);

        System.out.println("--- GÜNLÜK SİPARİŞ İŞLEME SİSTEMİ BAŞLADI ---");


        for (int i = 0; i < pendingOrders.length; i++) {
            Order currentOrder = pendingOrders[i];


            if (currentOrder.getTotalAmount() <= 0) {
                System.out.println("HATA: " + currentOrder.getOrderId() + " geçersiz tutar!");
                continue;
            }


            String userSelection = (currentOrder.getTotalAmount() > 10000) ? "CRYPTO" : "KREDI_KARTI";


            PaymentMethod selectedMethod = switch (userSelection) {
                case "KREDI_KARTI" -> new GarantiPos();
                case "CRYPTO" -> new CryptoWallet();
                default -> throw new IllegalArgumentException("Bilinmeyen ödeme yöntemi");
            };

            boolean isSuccess = selectedMethod.processPayment(currentOrder.getTotalAmount());

            if (isSuccess) {
                currentOrder.markAsPaid();
                System.out.println("BAŞARILI: " + currentOrder.getOrderId() + " ödendi.\n");
            }
        }
    }
}