package com.example.ex02.domain;

import lombok.Data;


// 참고용
//CREATE TABLE JUNGMIN_ORDER(
//ORDER_ID NUMBER CONSTRAINT PK_ORDER PRIMARY KEY,
//PRODUCT_ID NUMBER NOT NULL,
//PRODUCT_COUNT NUMBER DEFAULT 1,
//ORDER_DATE DATE DEFAULT SYSDATE,
//CONSTRAINT FK_ORDER_PRODUCT FOREIGN KEY(PRODUCT_ID)
//REFERENCES JUNGMIN_PRODUCT(PRODUCT_ID)
//);

@Data
public class OrderVO {
    private Long orderId;
    private Long productId;
    private int productCount;
    private String orderDate;
}












