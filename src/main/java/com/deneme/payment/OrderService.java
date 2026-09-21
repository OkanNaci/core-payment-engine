package com.deneme.payment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void processNewOrder(Order order) {
        System.out.println("OrderService: Sipariş PostgreSQL'e kaydediliyor -> " + order.getOrderId());
        orderRepository.save(order);
    }

    public void addProductToOrder(String orderId, Product newProduct) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı: " + orderId));

        order.addProduct(newProduct);
        orderRepository.save(order);
        System.out.println("Ürün sepete eklendi ve veritabanı güncellendi: " + newProduct.getName());
    }

    public void deleteOrder(String orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            orderRepository.deleteById(orderId);
            System.out.println("Sipariş veritabanından silindi: " + orderId);
        } else {
            throw new RuntimeException("Silinecek sipariş bulunamadı: " + orderId);
        }
    }

    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Güncellenecek sipariş bulunamadı: " + orderId));

        order.setStatus(newStatus);
        orderRepository.save(order);
        System.out.println("Sipariş durumu veritabanında güncellendi: " + orderId + " -> " + newStatus);
    }

    public Optional<Order> getOrder(String orderId) {
        return orderRepository.findById(orderId);
    }

    public double getDailyRevenue() {
        return orderRepository.calculateTotalRevenueByStatus(OrderStatus.COMPLETED);
    }
}