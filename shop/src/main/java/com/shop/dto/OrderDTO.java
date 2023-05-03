package com.shop.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// 주문할 상품의 상품 ID와 주문 수량을 전달받을 OrderDTO 객체 선언
@Data
public class OrderDTO {

    // 각 필드에 Validation 관련 어노테이션을 붙인다
    @NotNull(message = "상품 아이디는 필수 입력값입니다")
    private Long itemId; // 주문할 상품의 상품 ID

    @Min(value = 1, message = "최소 주문 수량은 1개입니다")
    @Max(value = 999, message = "최대 주문 수량은 999개입니다")
    private int count; // 주문 수량
    
}
