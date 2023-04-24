package com.example.ex04.mapper;

import com.example.ex04.domain.OrderVO;
import com.example.ex04.domain.ProductVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
//  상품 추가
    public void insert(ProductVO productVO);

//  상품 전체 목록
    public List<ProductVO> selectAll();

//  상품 재고 수정
    public void updateStock(OrderVO orderVO);
}
