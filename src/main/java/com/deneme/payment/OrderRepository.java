package com.deneme.payment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrderRepository {
    private final Map<String, Order> database = new HashMap<>();

    private final Set<String> paidOrders = new HashSet<>();

    public void save(Order order) {
        database.put(order.getOrderId(), order);
    }

    public Order findById(String orderId) {
        return database.get(orderId);
    }

    public void markOrderAsPaid(String orderId) {
        Order order = findById(orderId);
        if (order != null) {
            order.markAsPaid();
            paidOrders.add(orderId);
        }
    }

    public boolean isOrderPaid(String orderId) {
        return paidOrders.contains(orderId);
    }
}