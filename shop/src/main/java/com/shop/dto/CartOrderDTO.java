package com.shop.dto;

import lombok.Data;

import java.util.List;

// 장바구니 페이지에서 주문할 상품 데이터를 전달할 DTO

@Data
public class CartOrderDTO {
    private Long cartItemId;

    // CartOrderDTO 클래스가 자기 자신을 List로 가지고 있도록 한다.
    private List<CartOrderDTO> cartOrderDTOList;
}
