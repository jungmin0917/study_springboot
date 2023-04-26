package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JpaRepository를 상속받는 ItemRepository 인터페이스를 만든다
// JpaRepository에는 두 개의 제네릭 타입이 들어간다.
// 첫 번째에는 엔티티 타입 클래스, 두 번째에는 기본키의 자료형을 넣어준다.
// JpaRepository 객체에는 기초 CRUD 및 페이징 처리에 대한 메소드가 정의되어 있다.

// DAO의 역할로 사용할 Repository 선언함
public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    // 상품명으로 데이터를 조회 (반환값이 여러 개 있을 수 있으므로 List를 사용함)
    List<Item> findByItemNm(String itemNm);
    
    // 상품명 및 상품 상세 설명으로 데이터 조회
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    // 상품 가격보다 값이 작은 데이터 조회
    List<Item> findByPriceLessThan(Integer price); // 왜 그런지 모르겠는데 int가 아니고 래퍼 클래스를 썼다.

    // 상품 가격보다 값이 작은 데이터 조회인데 가격을 기준으로 내림차순
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("SELECT i FROM Item i WHERE i.itemDetail LIKE %:itemDetail% ORDER BY i.price DESC")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail); // 파라미터로 itemDetail을 받고 그것을 @Query 내부에서 :변수명 대신에 넣겠다는 뜻
    // Item i 로 Item 엔티티를 현재 쿼리에서 i라는 별칭으로 쓸 것임을 명시했다.
    // 여기서 i는 Item 엔티티의 별칭(alias)로 사용되고 있다.
    // JPQL에서는 엔티티와 연관된 데이터를 조회할 때, 엔티티에 대한 별칭을 사용할 수도 있다. (안 사용해도 됨)
    // 위와 같이 @Query가 달린 메소드는 그냥 바로 쿼리메소드 식으로 해석해서 실행하는 것이 아니고, 개발자가 @Query 내부에 적은 JPQL대로 실행한다.
}
