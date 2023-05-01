package com.shop.repository;

import com.shop.dto.ItemSearchDTO;
import com.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);
    // 상품 조회 조건을 담고 있는 ItemSearchDTO 객체와 페이징 정보를 담고 있는 Pageable 객체를 파라미터로 받는 getAdminItemPage 메소드를 정의한다. 반환 데이터로 Page<Item> 객체를 반환한다.
}
