package com.shop.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Data
public class Cart {

    @Id // 현재 컬럼을 PK로 지정
    @Column(name = "cart_id") // 매핑할 컬럼 지정
    @GeneratedValue(strategy = GenerationType.AUTO) // PK 자동생성 전략 설정
    private Long id;

    // OneToOne 어노테이션으로 회원 엔티티와 일대일로 매핑을 한다.
    @OneToOne // 일대일 매핑
    @JoinColumn(name = "member_id") // 매핑할 외래키 지정. name 속성에는 매핑할 외래키의 이름을 설정한다. @JoinColumn의 name을 명시하지 않으면 JPA가 알아서 ID를 찾지만, 컬럼명이 원하는 대로 생성되지 않을 경우를 대비해 직접 명시했다.
    private Member member; // 매핑할 때는 상대의 엔티티를 선언한다.


}
