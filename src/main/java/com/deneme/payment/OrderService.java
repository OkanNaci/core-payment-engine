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

    public Optional<Order> getOrder(String orderId) {
        return orderRepository.findById(orderId);
    }

    public double getDailyRevenue() {
        return orderRepository.calculateTotalRevenue();
    }
}