package com.shop.repository;


import com.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 회원 아이디로 장바구니 엔티티 조회하는 메소드
    Cart findByMemberId(Long memberId);
    
}
