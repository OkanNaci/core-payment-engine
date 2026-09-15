package com.deneme.payment;

import java.util.Optional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

public class OrderRepository {
    private final Map<String, Order> database = new HashMap<>();
    private final Set<String> paidOrders = new HashSet<>();

    public void save(Order order) {
        database.put(order.getOrderId(), order);
    }

    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(database.get(orderId));
    }

    public void markOrderAsPaid(String orderId) {
        findById(orderId).ifPresent(order -> {
            order.markAsPaid();
            paidOrders.add(orderId);
        });
    }

    public boolean isOrderPaid(String orderId) {
        return paidOrders.contains(orderId);
    }
    // import java.util.Collection; (gerekirse ekle)

    public double calculateTotalRevenue() {
        return database.values().stream() // Veritabanındaki tüm siparişleri nehre (Stream) dök
                .filter(order -> order.getStatus() == OrderStatus.COMPLETED) // Sadece ödenenleri filtrele
                .mapToDouble(Order::getTotalAmount) // Sadece sipariş tutarlarını al
                .sum(); // Hepsini topla
    }

}