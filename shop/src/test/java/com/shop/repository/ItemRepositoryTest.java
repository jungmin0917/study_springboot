package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest // 테스트 어노테이션
@TestPropertySource(locations="classpath:application-test.properties") // 테스트할 때 사용할 프로퍼티 파일 지정
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository; // 테스트할 Repository 인터페이스 주입받음

    @Test // 메소드 테스트 어노테이션
    @DisplayName("상품 저장 테스트") // 테스트명 지정
    public void createItemTest(){ // 아이템 생성 테스트
        // 여기서 아이템 엔티티를 생성 후 JpaRepository.save(엔티티객체) 메소드를 실행할 것이다
        // 그러면 insert 쿼리문을 작성하지 않고도 상품 테이블에 데이터를 insert할 수 있다.

        for (int i = 1; i <= 10; i++) { // 여러 개를 생성 후 아래 조회에서 테스트하기 위함임
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL); // ENUM 타입 접근은 ENUM클래스명.ENUM값 으로 접근한다
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item); // 엔티티 객체를 저장 후 savedItem 엔티티에 반환함

//        System.out.println(savedItem.toString());
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){
        this.createItemTest();

        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");

        for(Item item : itemList){
            System.out.println(item.toString()); // 해당 아이템의 전체 정보 출력
        }


    }

}















