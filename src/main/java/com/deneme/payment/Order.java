package com.deneme.payment;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String orderId;
    private boolean isPaid;


    private final List<Product> cart;

    public Order(String orderId) {
        this.orderId = orderId;
        this.isPaid = false;
        this.cart = new ArrayList<>();
    }

    public void addProduct(Product product) {
        cart.add(product);
    }
    public String getOrderId() {
        return orderId;
    }

    public double getTotalAmount() {
        double total = 0;
        for (Product item : cart) {
            total += item.getPrice();
        }
        return total;
    }

    public void markAsPaid() {
        this.isPaid = true;
    }

    public boolean isPaid() {
        return isPaid;
    }
}