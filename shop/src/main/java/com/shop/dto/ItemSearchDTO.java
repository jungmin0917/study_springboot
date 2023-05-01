package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Data;

// 상품 조회 조건을 가지고 있는 DTO.

@Data
public class ItemSearchDTO {
    private String searchDateType; // 현재 시간과 상품 등록일을 비교해서 상품 데이터를 조회함
    // all: 상품 등록일 전체, 1d: 최근 하루, 1w: 최근 일주일, 1m: 최근 한달, 6m: 최근 6개월
    private ItemSellStatus searchSellStatus; // 상품 판매상태 기준으로 조회
    private String searchBy; // 검색할 때 어떤 유형으로 검색할지 선택 (itemNm: 상품명, createdBy: 상품 등록자 아이디)
    private String searchQuery = ""; // 검색어 저장할 변수.
}
