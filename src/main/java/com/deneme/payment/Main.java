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

        OrderRepository repository = new OrderRepository();
        repository.save(myOrder);

        System.out.println("\n--- OPTIONAL TESTİ ---");

        repository.findById("MAIN-001").ifPresent(bulunanSiparis -> {
            System.out.println("Sipariş başarıyla bulundu! ID: " + bulunanSiparis.getOrderId());
        });

        repository.findById("HAYALET-999").ifPresentOrElse(
                bulunan -> System.out.println("Bu yazı asla yazdırılmayacak, çünkü sipariş yok!"),
                () -> System.out.println("Hata önlendi: HAYALET-999 ID'li sipariş veritabanında yok!")
        );

        // STREAM TESTİ İÇERİ ALINDI
        System.out.println("\n--- STREAM API TESTİ ---");
        System.out.println("Toplam Ciro: " + repository.calculateTotalRevenue() + " TL");

    }
}