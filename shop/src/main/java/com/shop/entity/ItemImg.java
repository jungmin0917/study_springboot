package com.shop.entity;

// 상품 이미지 엔티티

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "item_img")
@Data
public class ItemImg extends BaseEntity{

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO) // 사실 AUTO 옵션은 생략해도 기본값이라 상관 없음
    private Long id;

    private String imgName; // 이미지 파일명
    private String oriImgName; // 원본 이미지 파일명
    private String imgUrl; // 이미지 조회 경로
    private String repImgYn; // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY) // Item 엔티티와 다대일 매핑
    @JoinColumn(name = "item_id")
    private Item item;

    // 이미지 정보 업데이트하는 메소드
    public void updateItemImg(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}











