package com.deneme.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private final String orderId;
    private OrderStatus status; // Güvenlik kalkanımız
    private final List<Product> cart;

    public Order(String orderId) {
        this.orderId = orderId;
        this.status = OrderStatus.PENDING; // Varsayılan durum
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