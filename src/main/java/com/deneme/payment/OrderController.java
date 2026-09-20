package com.deneme.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order newOrder) {
        orderService.processNewOrder(newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        Optional<Order> order = orderService.getOrder(orderId);

        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/{orderId}/products")
    public ResponseEntity<Order> addProductToOrder(@PathVariable String orderId, @RequestBody Product newProduct) {
        try {
            orderService.addProductToOrder(orderId, newProduct);

            return ResponseEntity.ok(orderService.getOrder(orderId).get());

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
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus status) {
        try {
            orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(orderService.getOrder(orderId).get());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/revenue")
    public ResponseEntity<Double> getRevenue() {
        return ResponseEntity.ok(orderService.getDailyRevenue());
    }

}