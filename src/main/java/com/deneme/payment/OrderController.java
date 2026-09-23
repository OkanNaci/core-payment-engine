package com.deneme.payment;

import com.deneme.payment.dto.CreateOrderRequest;
import com.deneme.payment.dto.OrderResponse;
import com.deneme.payment.dto.RevenueResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String orderId) {
        try {
            OrderResponse response = orderService.getOrder(orderId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{orderId}/products")
    public ResponseEntity<OrderResponse> addProductToOrder(@PathVariable String orderId, @RequestBody Product newProduct) {
        try {
            orderService.addProductToOrder(orderId, newProduct);
            return ResponseEntity.ok(orderService.getOrder(orderId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus status) {
        try {
            orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(orderService.getOrder(orderId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<RevenueResponse> getRevenue() {
        return ResponseEntity.ok(orderService.getDailyRevenue());
    }
}