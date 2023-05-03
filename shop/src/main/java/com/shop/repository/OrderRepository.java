package com.shop.repository;

import com.shop.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // @Query 어노테이션으로 해당 사용자의 주문 내역 조회
    @Query("SELECT o FROM Order o " +
            "WHERE o.member.email = :email " +
            "ORDER BY o.orderDate DESC")
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    // 해당 사용자의 주문 개수 조회
    @Query("SELECT COUNT(o) FROM Order o " +
            "WHERE o.member.email = :email")
    Long countOrder(@Param("email") String email);
}
