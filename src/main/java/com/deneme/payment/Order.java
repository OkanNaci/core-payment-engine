package com.deneme.payment;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    private String orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<Product> cart = new ArrayList<>();


    protected Order() {
    }

    public Order(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public List<Product> getCart() {
        return cart;
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