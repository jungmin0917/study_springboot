package com.example.ex03.dao;

import com.example.ex03.domain.ProductVO;
import com.example.ex03.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

// 원래는 DAO쪽도 인터페이스로 추상화하는 경우가 많으나, 지금은 편의상 그냥 구현하였음

@Repository
@RequiredArgsConstructor
public class ProductDAO {
    // 여기서 주입하면 필드 주입이 된다. 이 구현체를 다른 곳에서 @RequiredArgsConstructor로 어노테이션을 해 놓으면, 생성자 주입이 되는 것이다.
    private final ProductMapper productMapper;

    // 상품 추가
    public void save(ProductVO productVO){ // 일반적으로 DAO에서는 save라고 함
        productMapper.insert(productVO);
    }

    // 상품 전체 목록
    public List<ProductVO> findAll(){ // 일반적으로 DAO에서는 findAll이라고 함
        return productMapper.selectAll();
    }
}
