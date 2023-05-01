package com.shop.repository;

import com.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId); // 아이템 id를 받아 그걸로 상품 이미지를 조회하는데 상품 이미지 아이디가 오름차순으로 되게끔 조회함
}
