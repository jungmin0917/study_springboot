CREATE SEQUENCE SEQ_ORDER;

CREATE TABLE JUNGMIN_PRODUCT(
	PRODUCT_ID NUMBER CONSTRAINT PK_PRODUCT PRIMARY KEY, -- 상품 ID
	PRODUCT_NAME VARCHAR2(500) NOT NULL, -- 상품 이름
	PRODUCT_STOCK NUMBER DEFAULT 0, -- 상품 재고
	PRODUCT_PRICE NUMBER DEFAULT 0, -- 상품 가격
	REGISTER_DATE DATE DEFAULT SYSDATE,
	UPDATE_DATE DATE DEFAULT SYSDATE
);

CREATE TABLE JUNGMIN_ORDER(
	ORDER_ID NUMBER CONSTRAINT PK_ORDER PRIMARY KEY,
	PRODUCT_ID NUMBER NOT NULL,
	PRODUCT_COUNT NUMBER DEFAULT 1,
	ORDER_DATE DATE DEFAULT SYSDATE,
	CONSTRAINT FK_ORDER_PRODUCT FOREIGN KEY(PRODUCT_ID)
	REFERENCES JUNGMIN_PRODUCT(PRODUCT_ID)
);

SELECT * FROM JUNGMIN_PRODUCT ORDER BY PRODUCT_ID DESC;

SELECT * FROM JUNGMIN_ORDER ORDER BY ORDER_ID DESC;

