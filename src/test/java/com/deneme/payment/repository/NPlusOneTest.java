package com.deneme.payment.repository;

import com.deneme.payment.entity.Order;
import com.deneme.payment.entity.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class NPlusOneTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void nPlusOneProbleminiGoster() {


        for (int i = 1; i <= 3; i++) {
            Order order = new Order("ORD-NPLUS-" + i);

            Product p1 = new Product("MacBook Pro " + i, new BigDecimal("75000"));
            Product p2 = new Product("Magic Mouse " + i, new BigDecimal("3500"));

            order.addProduct(p1);
            order.addProduct(p2);

            orderRepository.save(order);
        }


        entityManager.flush();
        entityManager.clear();

        System.out.println("\n=========== 1. ANA SORGU BAŞLIYOR (Tüm Siparişleri Çek) ===========");

        List<Order> orders = orderRepository.findAllWithCart();
        System.out.println("=========== ANA SORGU BİTTİ ===========\n");


        System.out.println("=========== N TANE ALT SORGU BAŞLIYOR (Sepetleri Oku) ===========");

        for (Order order : orders) {
            System.out.println("Sipariş ID: " + order.getOrderId() + " -> Toplam Tutar: " + order.getTotalAmount());
        }

        System.out.println("=========== SORGULAR BİTTİ ===========\n");
    }
}