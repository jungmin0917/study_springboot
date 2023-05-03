package com.shop.exception;

// 재고의 수가 상품 주문 수량보다 적을 때 발생시킬 Exception을 정의함.
public class OutOfStockException extends RuntimeException{
    // message를 매개변수로 받는 생성자를 선언함
    public OutOfStockException(String message){
        super(message);
    }
}
