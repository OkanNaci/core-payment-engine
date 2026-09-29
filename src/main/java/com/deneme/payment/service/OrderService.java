package com.deneme.payment.service;

import com.deneme.payment.entity.Order;
import com.deneme.payment.mapper.OrderMapper;
import com.deneme.payment.repository.OrderRepository;
import com.deneme.payment.entity.OrderStatus;
import com.deneme.payment.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.deneme.payment.dto.CreateOrderRequest;
import com.deneme.payment.dto.OrderResponse;
import com.deneme.payment.dto.RevenueResponse;

@Service
@Transactional
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    public OrderService(OrderRepository orderRepository , OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public OrderResponse processNewOrder(CreateOrderRequest request) {
        Order newOrder = new Order(request.orderId());

        log.info("Sipariş PostgreSQL'e kaydediliyor -> {}", newOrder.getOrderId());
        Order savedOrder = orderRepository.save(newOrder);

        return orderMapper.toResponse(savedOrder);
    }

    public void addProductToOrder(String orderId, Product newProduct) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı: " + orderId));

        order.addProduct(newProduct);
        orderRepository.save(order);
        log.info("Ürün sepete eklendi ve veritabanı güncellendi: {}", newProduct.getName());    }

    public void deleteOrder(String orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            orderRepository.deleteById(orderId);
            log.info("Sipariş veritabanından silindi: {}", orderId);        } else {
            throw new RuntimeException("Silinecek sipariş bulunamadı: " + orderId);
        }
    }

    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Güncellenecek sipariş bulunamadı: " + orderId));

        order.setStatus(newStatus);
        orderRepository.save(order);
        log.info("Sipariş durumu veritabanında güncellendi: {} -> {}", orderId, newStatus);    }

    public OrderResponse getOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı: " + orderId));

        return orderMapper.toResponse(order);
    }

    public Page<OrderResponse> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderId").descending());

        Page<Order> orderPage = orderRepository.findAll(pageable);

        return orderPage.map(orderMapper::toResponse);
    }

    public RevenueResponse getDailyRevenue() {
        BigDecimal total = orderRepository.calculateTotalRevenueByStatus(OrderStatus.COMPLETED);
        return new RevenueResponse(total, "TRY", LocalDateTime.now());
    }


}