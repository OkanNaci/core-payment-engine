package com.deneme.payment.repository;

import com.deneme.payment.entity.Order;
import com.deneme.payment.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT COALESCE(SUM(p.price), 0) FROM Order o JOIN o.cart p WHERE o.status = :status")
    BigDecimal calculateTotalRevenueByStatus(@Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o JOIN FETCH o.cart")
    List<Order> findAllWithCart();
}