package com.example.ex02.mapper;

import com.example.ex02.domain.ProductVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    // 상품 추가
    public void insert(ProductVO productVO);

    // 상품 전체 목록
    public List<ProductVO> selectAll();
}
