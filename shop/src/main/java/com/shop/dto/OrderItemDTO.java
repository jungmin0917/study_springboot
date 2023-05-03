package com.shop.dto;

import com.shop.entity.OrderItem;
import lombok.Data;

// 주문 데이터를 화면에 전송할 DTO

@Data
public class OrderItemDTO {

    private String itemNm; // 상품명
    private int count; // 주문 수량
    private int orderPrice; // 주문 금액
    private String imgUrl; // 상품 이미지 경로

    public OrderItemDTO(OrderItem orderItem, String imgUrl) {
        // OrderItemDTO 클래스의 생성자로 OrderItem 엔티티 객체와 이미지 경로를 파라미터로 받아 필드값을 세팅한다.
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }


}
