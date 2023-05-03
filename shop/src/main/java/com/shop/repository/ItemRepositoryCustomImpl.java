package com.shop.repository;


import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemSearchDTO;
import com.shop.dto.MainItemDTO;
import com.shop.dto.QMainItemDTO;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import com.shop.entity.QItemImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{ // ItemRepositoryCustom을 상속받는다

    private JPAQueryFactory queryFactory; // 동적으로 쿼리를 생성하기 위해 JPAQueryFactory 클래스를 사용한다.

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em); // JPAQueryFactory를 생성하려면 EntityManager가 필요함.
        // EntityManager가 JPA에서 DB와의 연결을 관리하는 객체임.
    }

    // 상품 판매 상태 조건
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus); // 상품 판매 조건이 전체(null)인 경우 null을 리턴. 결과값이 null이면 WHERE절에서 해당 조건은 무시된다. 상품 판매 조건이 판매중 또는 품절처럼 존재하는 상태라면 해당 조건의 상품만 조회한다.
    }

    // 상품 등록된 시간 조건
    private BooleanExpression regDtsAfter(String searchDateType){ // Dts는 Dates의 줄임말인 듯?
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){ // 등록된 시간이 전체이거나 null인 경우
            return null;
        }else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        }else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        }else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
    }

    // 검색어로 검색할 때 조건 (상품명, 상품 등록자 아이디로)
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("itemNm", searchBy)){ // 검색 종류가 상품명인 경우
            return QItem.item.itemNm.like("%" + searchQuery + "%");
        }else if(StringUtils.equals("createdBy", searchBy)){ // 검색 종류가 상품 등록자 아이디인 경우
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }
        
        return null; // 그 외 다른 경우
    }

    // 검색어로 검색할 때 조건 (상품명으로)
    private BooleanExpression itemNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery + "%");
    }

    @Override
    // 상품 관리 리스트를 조회하여 Page 객체(JPA에서 제공하는)로 반환하는 메소드. 이 Page 객체는 페이지 정보 및 페이징된 결과 데이터 등을 담고 있다.
    public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {

        // JPAQueryFactory 객체를 이용해서 쿼리를 생성함.
        QueryResults<Item> results = queryFactory
                .selectFrom(QItem.item) // item 엔티티들 대상으로 조회하겠다는 거임
                // WHERE 조건절 : BooleanExpression을 반환하는 조건문들을 넣어준다. ',' 단위로 넣어줄 경우 and 조건으로 인식한다.
                .where(regDtsAfter(itemSearchDTO.getSearchDateType()),
                        searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
                        searchByLike(itemSearchDTO.getSearchBy(), itemSearchDTO.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset()) // 데이터를 가지고 올 시작 인덱스 지정
                .limit(pageable.getPageSize()) // 한 번에 가지고 올 최대 개수 지정 (페이지 하나 당 개수)
                .fetchResults(); // 조회한 리스트 및 전체 개수를 포함하는 QueryResults를 반환함. 상품 데이터 리스트 조회 및 상품 데이터 전체 개수를 조회하는 2번의 쿼리문이 실행된다.

        List<Item> content = results.getResults(); // 조회한 데이터를 Item 엔티티 타입의 List에 담는다
        Long total = results.getTotal(); // 조회한 전체 데이터를 전체 개수 total 변수에 담는다

        return new PageImpl<>(content, pageable, total); // 조회한 데이터를 Page 클래스의 구현체인 PageImpl 객체로 반환한다.
    }

    // 메인 페이지에 보여줄 리스트 조회하는 메소드
    @Override
    public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        QueryResults<MainItemDTO> results = queryFactory
                .select(
                        new QMainItemDTO(
                                item.id,
                                item.itemNm,
                                item.itemDetail,
                                itemImg.imgUrl,
                                item.price
                        )
                )
                .from(itemImg) // itemImg에서 조회하는데
                .join(itemImg.item, item) // ItemImg 테이블이랑 Item 테이블이랑 내부 조인함
                .where(itemImg.repImgYn.eq("Y")) // itemImg의 repImgYn 값이 Y인 것만 (대표 이미지만)
                .where(itemNmLike(itemSearchDTO.getSearchQuery())) // 그 중 검색어가 있으면 검색어도 조건에 추가
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults(); // QueryResults 객체로 반환

        List<MainItemDTO> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
