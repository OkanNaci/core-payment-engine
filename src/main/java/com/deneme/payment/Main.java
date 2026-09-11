package com.deneme.payment;

public class Main {
    public static void main(String[] args) {
        // KAVRAMLAR: Arrays, Objects
        // 3 adet sipariş oluşturup belleğe (Heap) diziyoruz
        Order[] pendingOrders = new Order[3];
        pendingOrders[0] = new Order("ORD-001", 1500.50);
        pendingOrders[1] = new Order("ORD-002", -50.0); // Hatalı veri denemesi
        pendingOrders[2] = new Order("ORD-003", 45000.0);

        System.out.println("--- GÜNLÜK SİPARİŞ İŞLEME SİSTEMİ BAŞLADI ---");

        // KAVRAMLAR: Loops (for), Variable
        for (int i = 0; i < pendingOrders.length; i++) {
            Order currentOrder = pendingOrders[i];

            // KAVRAMLAR: If/Else, Operators
            // Guard Clause: Hatalı tutarları filtrele
            if (currentOrder.getTotalAmount() <= 0) {
                System.out.println("HATA: " + currentOrder.getOrderId() + " geçersiz tutar!");
                continue; // Bu siparişi atla, döngünün sonrakine geç
            }

            // Hangi ödeme yönteminin seçileceğine simülasyon amaçlı karar verelim
            // (Gerçek hayatta bu bilgi kullanıcıdan web üzerinden gelir)
            String userSelection = (currentOrder.getTotalAmount() > 10000) ? "CRYPTO" : "KREDI_KARTI";

            // KAVRAMLAR: Switch Expression (Modern Java)
            PaymentMethod selectedMethod = switch (userSelection) {
                case "KREDI_KARTI" -> new GarantiPos();
                case "CRYPTO" -> new CryptoWallet();
                default -> throw new IllegalArgumentException("Bilinmeyen ödeme yöntemi");
            };

            // POLİMORFİZMİN GÜCÜ:
            // Sistem şu an Garanti mi yoksa Crypto mu çalıştırıyor BİLMİYOR.
            // Sadece "processPayment" komutunu veriyor. İşi arka plan kendi hallediyor.
            boolean isSuccess = selectedMethod.processPayment(currentOrder.getTotalAmount());

            if (isSuccess) {
                currentOrder.markAsPaid(); // Encapsulation sayesinde güvenli güncelleme
                System.out.println("BAŞARILI: " + currentOrder.getOrderId() + " ödendi.\n");
            }
        }
    }
}