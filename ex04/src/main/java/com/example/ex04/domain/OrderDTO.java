package com.example.ex04.domain;

// Order와 Product의 필드들이 일부 조합된 객체 (데이터 전달용)

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class OrderDTO {
    private Long productId;
    private String productName;
    private int productStock;
    private int productPrice;
    private String registerDate;
    private String updateDate;
    private Long orderId;
    private Long productCount;
    private String orderDate;
    private Long orderPrice; // 전체 주문 금액
}
