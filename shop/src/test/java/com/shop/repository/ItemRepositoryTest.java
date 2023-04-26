package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest // 테스트 어노테이션
@TestPropertySource(locations="classpath:application-test.properties") // 테스트할 때 사용할 프로퍼티 파일 지정
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository; // 테스트할 Repository 인터페이스 주입받음

    @PersistenceContext // 엔티티 매니저를 컨테이너가 자동으로 생성하고 주입하도록 함
    EntityManager em; // 직접 생성하지 않으므로 의존성 관리가 편하다

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
    @DisplayName("상품 저장 테스트2")
    public void createItemTest2(){
        for (int i = 1; i <= 5; i++) { // 여러 개를 생성 후 아래 조회에서 테스트하기 위함임
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
        for (int i = 6; i <= 10; i++) { // 여러 개를 생성 후 아래 조회에서 테스트하기 위함임
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLDOUT); // ENUM 타입 접근은 ENUM클래스명.ENUM값 으로 접근한다
            item.setStockNumber(0);
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

    @Test
    @DisplayName("상품명 및 상품상세설명 조회 (Or) 테스트")
    public void findByItemNmOrItemDetailTest(){
        this.createItemTest(); // 일단 상품 여러 개 생성

        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");

        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격보다 작은 데이터 조회 (LessThan) 테스트")
    public void findByPriceLessThanTest(){
        this.createItemTest(); // 일단 상품 만듦

        List<Item> itemList = itemRepository.findByPriceLessThan(10005);

        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격보다 작은 데이터를 가격 기준 내림차순으로 조회 (OrderBy) 테스트")
    public void findByPriceLessThanOrderByPriceDescTest(){
        this.createItemTest();

        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);

        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest(){
        this.createItemTest(); // 일단 상품 등록함

        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");

        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest(){
        this.createItemTest();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em); // 주입받은 EntityManger로 JPAQueryFactory를 생성함. 쿼리를 동적으로 생성하기 위한 팩토리 객체

        QItem qItem = QItem.item; // 쿼리를 생성하기 위해 플러그인을 통해 자동으로 생성된 QItem 객체를 이용한다. (일반 Item 엔티티 객체로는 쿼리 동적 생성이 안 되므로 주의할 것)

        // PHP에서 했던 것처럼 .select.where.where.orderBy 이렇게 여러 쿼리를 이어서 쓸 수 있다
        // 즉 평소엔 메소드 쿼리 쓰고, 메소드 쿼리를 사용하기엔 복잡한 로직의 경우 Querydsl을 이용하면 될 것 같다.
        // JPAQuery<엔티티명> 쿼리명 = queryFactory.각종 쿼리 메소드().각종 쿼리 메소드();
        JPAQuery<Item> query = queryFactory
                .selectFrom(qItem) // Item 엔티티에 대한 메타모델 정보를 담고 있는 qItem에서 찾음
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL)) // itemSellStatus가 SELL인 것을 찾음
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());

        // 위와 같이 JPAQuery 객체를 만들었으면 이걸 fetch 한다

        List<Item> itemList = query.fetch(); // fetchAll하지 않고 그냥 fetch해도 정보가 전부 담김 (웬만하면 fetchAll() 대신 fetch() 사용할 것). 반환값이 List 자료형으로 반환된다

        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트 2")
    public void queryDslTest2(){
        this.createItemTest2();

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // 쿼리에 들어갈 조건을 만들어주는 빌더
        // BooleanBuilder 클래스는 Predicate를 구현하고 있으며 메소드 체인 형태로 사용할 수 있다.

        QItem item = QItem.item;

        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
        booleanBuilder.and(item.price.gt(price)); // gt() : greater than (~~보다 큰)

        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){ // 만약 두 값이 같으면 (아마 enum값과 String 비교하려고 넣은 듯?), 판매상태가 SELL일 때만 BooleanBuilder에 판매상태 조건을 동적으로 추가함
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL)); // 조건 추가
        }

        Pageable pageable = PageRequest.of(0, 5); // 데이터를 페이징해서 조회하도록 PageRequest.of() 메소드를 이용해 Pageable 객체를 생성함. 첫 번째 인자는 조회할 페이지의 번호, 두 번째 인자는 한 페이지당 조회할 데이터의 개수이다.

        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable); // JpaRepository의 findAll() 메소드로 찾을 건데, 매개변수로 빌드한 쿼리 객체, 페이지 객체 넣고 반환값은 Page 객체임

        // 이렇게 Page 객체로 받으면, 조회한 데이터의 총 개수라든지, 데이터의 정보라든지 메소드로 골라서 써먹을 수 있음.

        System.out.println("total elements : " + itemPagingResult.getTotalElements()); // 조회한 데이터 총 개수
        List<Item> resultItemList = itemPagingResult.getContent();

        for(Item resultItem : resultItemList){
            System.out.println(resultItem.toString());
        }

    }
}















