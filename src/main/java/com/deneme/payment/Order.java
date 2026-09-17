package com.deneme.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private String orderId; // Jackson'ın değiştirebilmesi için 'final' kaldırıldı
    private OrderStatus status = OrderStatus.PENDING;
    private List<Product> cart = new ArrayList<>(); // 'final' kaldırıldı

    // 1. Spring Boot (Jackson) için ZORUNLU Boş Constructor
    public Order() {
    }

    // 2. Bizim eskiden yazdığımız manuel Constructor
    public Order(String orderId) {
        this.orderId = orderId;
    }

    // 3. Jackson'ın dışarıdan gelen ID'yi içeri yazabilmesi için Setter
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void addProduct(Product product) {
        cart.add(product);
    }

    public String getOrderId() {
        return orderId;
    }

    // Bu sadece hesaplama yapar, JSON'dan dışarıdan değer kabul etmez
    public double getTotalAmount() {
        double total = 0;
        for (Product item : cart) {
            total += item.price();
        }
        return total;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public boolean isPaid() {
        return this.status == OrderStatus.COMPLETED;
    }

    public void markAsPaid() {
        this.status = OrderStatus.COMPLETED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}