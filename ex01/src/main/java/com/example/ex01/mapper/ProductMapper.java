package com.example.ex01.mapper;

import com.example.ex01.domain.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

@Mapper // 현재 인터페이스가 매퍼 인터페이스임을 명시
public interface ProductMapper {
//    상품 추가
    public void insert(ProductVO productVO);

//    상품 조회
    @Select("SELECT PRODUCT_ID, PRODUCT_NAME, PRODUCT_STOCK, PRODUCT_PRICE, REGISTER_DATE, UPDATE_DATE FROM JUNGMIN_PRODUCT WHERE PRODUCT_ID = #{productId}")
    public ProductVO select(@Param("productId") Long productId); // PathVariable은 GetMapping 이런 곳에 쓰는 것임

//    상품 수정
//    상품 삭제
//    상품 전체 조회
}
