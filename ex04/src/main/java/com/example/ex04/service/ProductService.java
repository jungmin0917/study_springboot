package com.example.ex04.service;

// 똑같은 틀에 대해 여러 구현체가 있을 수 있으니 추상화를 하는 것이다
// 다만 그 구현체들은 세밀한 부분에서는 다를 것이다. 그렇기에 틀을 만드는 것이다.
// 예를 들어 게시판 중에는 자유게시판, 리뷰게시판 등이 있듯이 말이다.

// Service 계층에서 Mapper를 바로 쓰면 안 되고,
// DAO 구현체를 거쳐서 사용해야 한다

// DAO에서는 메소드 작명 시 해당 메소드에 어떤 쿼리가 들어있는지 정확하고 길게 쓰는 편이다.

import com.example.ex04.domain.ProductVO;

import java.util.List;

// 기존 Mapper 인터페이스의 메소드와 비슷하지만 메소드명을 좀 더 서비스화하고,
// 반환값이 혹시 필요하거나 다른 경우에는 그 부분을 바꾼다.
public interface ProductService {
    // 상품 추가
    public void register(ProductVO productVO);

    // 상품 전체 목록
    public List<ProductVO> getList();

    // 상품 재고 업데이트 쪽은 여기가 아니고 OrderService 쪽에 넣어놓는다.
}
