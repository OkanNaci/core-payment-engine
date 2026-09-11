
package com.deneme.payment;

public class Order {
    private String orderId;
    private double totalAmount;
    private boolean isPaid;

    // Constructor
    public Order(String orderId, double totalAmount) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.isPaid = false;
    }

    // ENCAPSULATION
    public double getTotalAmount() {
        return totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public boolean isPaid() {
        return isPaid;
    }


    public void markAsPaid() {
        this.isPaid = true;
    }
}