package com.deneme.payment;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public void processNewOrder(Order order) {
        System.out.println("OrderService: Sipariş işleme alınıyor -> " + order.getOrderId());
        orderRepository.save(order);
    }

    public void addProductToOrder(String orderId, Product newProduct) {

        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.addProduct(newProduct);
            orderRepository.save(order);
            System.out.println("Ürün sepete eklendi: " + newProduct.name());
        } else {
            throw new RuntimeException("Sipariş bulunamadı: " + orderId);
        }
    }
    public void deleteOrder(String orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            orderRepository.delete(orderId);
            System.out.println("Sipariş sistemden silindi: " + orderId);
        } else {
            throw new RuntimeException("Silinecek sipariş bulunamadı: " + orderId);
        }
    }

    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Güncellenecek sipariş bulunamadı: " + orderId));

        order.setStatus(newStatus);
        orderRepository.save(order);
        System.out.println("Sipariş durumu güncellendi: " + orderId + " -> " + newStatus);
    }

    public Optional<Order> getOrder(String orderId) {
        return orderRepository.findById(orderId);
    }

    public double getDailyRevenue() {
        return orderRepository.calculateTotalRevenue();
    }
}