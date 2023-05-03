package com.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MainItemDTO {
    private Long id;
    private String itemNm;
    private String itemDetail;
    private String imgUrl;
    private Integer price;


    // 해당 DTO의 생성자를 넣는데, 그 위에 @QueryProjection 어노테이션을 붙여준다.
    // 이렇게 하면, Querydsl로 결과 조회 시 QMainItemDTO로 반환값을 설정함으로써 엔티티 객체가 아닌 자동 변환된 DTO 객체로 반환을 받을 수 있어서 편리하게 사용할 수 있다.
    // 오해하지 마라.. 생성자에는 원래 반환값이 존재하지 않는다 = =;;
    @QueryProjection
    public MainItemDTO(Long id, String itemNm, String itemDetail, String imgUrl, Integer price) {
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
