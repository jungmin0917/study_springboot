package com.example.ex03.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

// 참고용
//CREATE TABLE JUNGMIN_ORDER(
//ORDER_ID NUMBER CONSTRAINT PK_ORDER PRIMARY KEY,
//PRODUCT_ID NUMBER NOT NULL,
//PRODUCT_COUNT NUMBER DEFAULT 1,
//ORDER_DATE DATE DEFAULT SYSDATE,
//CONSTRAINT FK_ORDER_PRODUCT FOREIGN KEY(PRODUCT_ID)
//REFERENCES JUNGMIN_PRODUCT(PRODUCT_ID)
//);

@Component
@Data
public class OrderVO {
    private Long orderId;
    private Long productId;
    private int productCount;
    private String orderDate;
}
