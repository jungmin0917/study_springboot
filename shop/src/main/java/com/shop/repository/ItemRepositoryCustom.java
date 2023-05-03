package com.shop.repository;

import com.shop.dto.ItemSearchDTO;
import com.shop.dto.MainItemDTO;
import com.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    
    // 상품 관리 페이지에 필요한 상품 리스트 조회 메소드
    Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);
    // 상품 조회 조건을 담고 있는 ItemSearchDTO 객체와 페이징 정보를 담고 있는 Pageable 객체를 파라미터로 받는 getAdminItemPage 메소드를 정의한다. 반환 데이터로 Page<Item> 객체를 반환한다.

    // 메인 페이지에 보여줄 상품 리스트 조회 메소드
    // 위 메소드와 다르게 엔티티가 아닌 DTO 객체를 반환받는다 (QueryProjection 어노테이션)
    Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);
}
