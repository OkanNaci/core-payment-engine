
package com.deneme.payment;

// KAVRAMLAR: Class, Encapsulation, Reference Types
public class Order {
    private String orderId;       // Reference Type (String)
    private double totalAmount;   // Primitive Type (double)
    private boolean isPaid;       // Primitive Type (boolean)

    // Constructor
    public Order(String orderId, double totalAmount) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.isPaid = false; // Başlangıçta ödenmedi
    }

    // ENCAPSULATION: Dışarıdan sadece okumaya izin veriyoruz (Getter)
    public double getTotalAmount() {
        return totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    // ENCAPSULATION: Ödeme durumunu değiştirmeyi kontrollü bir metoda bağlıyoruz
    public void markAsPaid() {
        this.isPaid = true;
    }
}