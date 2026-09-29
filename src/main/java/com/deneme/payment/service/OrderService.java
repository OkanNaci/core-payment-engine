package com.deneme.payment.service;

import com.deneme.payment.entity.Order;
import com.deneme.payment.repository.OrderRepository;
import com.deneme.payment.entity.OrderStatus;
import com.deneme.payment.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.deneme.payment.dto.CreateOrderRequest;
import com.deneme.payment.dto.OrderResponse;
import com.deneme.payment.dto.RevenueResponse;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse processNewOrder(CreateOrderRequest request) {
        Order newOrder = new Order(request.orderId());

        System.out.println("OrderService: Sipariş PostgreSQL'e kaydediliyor -> " + newOrder.getOrderId());
        Order savedOrder = orderRepository.save(newOrder);

        return mapToResponse(savedOrder);
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

    public OrderResponse getOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı: " + orderId));

        return mapToResponse(order);
    }

    public RevenueResponse getDailyRevenue() {
        BigDecimal total = orderRepository.calculateTotalRevenueByStatus(OrderStatus.COMPLETED);
        return new RevenueResponse(total, "TRY", LocalDateTime.now());
    }

    private OrderResponse mapToResponse(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getStatus().name(),
                order.getTotalAmount()
        );
    }
}