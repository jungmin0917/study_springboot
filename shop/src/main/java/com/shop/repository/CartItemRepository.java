package com.shop.repository;

import com.shop.dto.CartDetailDTO;
import com.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // 장바구니 ID, 상품 ID로 해당 상품이 장바구니에 들어있는지 조회하는 메소드
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    // 장바구니 상품 테이블과 상품 테이블을 조인하여 장바구니 아이디로 장바구니 상품을 조회한다
    @Query("SELECT new com.shop.dto.CartDetailDTO(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) FROM CartItem ci, ItemImg im JOIN ci.item i WHERE ci.cart.id = :cartId AND im.item.id = ci.item.id AND im.repImgYn = 'Y' ORDER BY ci.regTime DESC")
    List<CartDetailDTO> findCartDetailDTOList(Long cartId);

}
