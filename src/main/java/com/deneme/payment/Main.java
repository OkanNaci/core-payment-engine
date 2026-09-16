package com.deneme.payment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner testWiring(OrderService orderService) {
        return args -> {
            System.out.println("\n--- Tesisat Testi Başlıyor ---");

            Order order = new Order("SPRING-TEST-001");
            order.addProduct(new Product("Mekanik Klavye", 2500.0));

            orderService.processNewOrder(order);

            orderService.getOrder("SPRING-TEST-001").ifPresent(savedOrder ->
                    System.out.println("Başarılı! Sistemden çekilen sipariş tutarı: " + savedOrder.getTotalAmount())
            );

            System.out.println("--- Tesisat Testi Tamamlandı ---\n");
        };
    }
}