package com.shop.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer orderPrice; // 주문 가격
    private Integer count; // 수량

//    공통으로 상속받았음
//    private LocalDateTime regTime;
//    private LocalDateTime updateTime;
}
