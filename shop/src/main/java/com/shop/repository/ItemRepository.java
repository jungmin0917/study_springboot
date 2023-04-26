package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository를 상속받는 ItemRepository 인터페이스를 만든다
// JpaRepository에는 두 개의 제네릭 타입이 들어간다.
// 첫 번째에는 엔티티 타입 클래스, 두 번째에는 기본키의 자료형을 넣어준다.
// JpaRepository 객체에는 기초 CRUD 및 페이징 처리에 대한 메소드가 정의되어 있다.

// DAO의 역할로 사용할 Repository 선언함
public interface ItemRepository extends JpaRepository<Item, Long> {

    // 상품명으로 데이터를 조회하는 메소드 선언 (같은 상품명이 여러 개 있을 수 있으므로 List를 사용함)
    List<Item> findByItemNm(String itemNm);


}
