package com.deneme.payment.controller;

import com.deneme.payment.service.OrderService;
import com.deneme.payment.entity.OrderStatus;
import com.deneme.payment.entity.Product;
import com.deneme.payment.dto.CreateOrderRequest;
import com.deneme.payment.dto.OrderResponse;
import com.deneme.payment.dto.RevenueResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderResponse response = orderService.processNewOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<OrderResponse> response = orderService.getAllOrders(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String orderId) {
            OrderResponse response = orderService.getOrder(orderId);
            return ResponseEntity.ok(response);
    }

    @PostMapping("/{orderId}/products")
    public ResponseEntity<OrderResponse> addProductToOrder(@PathVariable String orderId, @RequestBody Product newProduct) {
            orderService.addProductToOrder(orderId, newProduct);
            return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
            orderService.deleteOrder(orderId);
            return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus status) {

            orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(orderService.getOrder(orderId));

    }

    @GetMapping("/revenue")
    public ResponseEntity<RevenueResponse> getRevenue() {
        return ResponseEntity.ok(orderService.getDailyRevenue());
    }
}