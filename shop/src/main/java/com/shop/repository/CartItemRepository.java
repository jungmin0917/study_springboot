package com.shop.repository;

import com.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // 장바구니 ID, 상품 ID로 해당 상품이 장바구니에 들어있는지 조회하는 메소드
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
}
