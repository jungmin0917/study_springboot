package com.example.ex01.domain;


// 참고용
//      CREATE TABLE JUNGMIN_PRODUCT(
//        PRODUCT_ID NUMBER CONSTRAINT PK_PRODUCT PRIMARY KEY, -- 상품 ID
//        PRODUCT_NAME VARCHAR2(500) NOT NULL, -- 상품 이름
//        PRODUCT_STOCK NUMBER DEFAULT 0, -- 상품 재고
//        PRODUCT_PRICE NUMBER DEFAULT 0, -- 상품 가격
//        REGISTER_DATE DATE DEFAULT SYSDATE,
//        UPDATE_DATE DATE DEFAULT SYSDATE
//        );

import lombok.Data;

@Data
public class ProductVO {
    private Long productId;
    private String productName;
    private Long productStock;
    private Long productPrice;
    private String registerDate;
    private String updateDate;
}
