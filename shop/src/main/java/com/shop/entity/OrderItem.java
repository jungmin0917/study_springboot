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

    // 매개변수로 Item 엔티티와 주문 개수를 입력받아 OrderItem 엔티티를 반환하는 메소드
    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice()); // 해당 주문한 상품 1개의 가격을 지정

        item.removeStock(count); // 해당 아이템 엔티티에서 주문 수량만큼 재고를 감소시킴

        return orderItem; // 주문 상품 엔티티를 반환함.
    }

    public int getTotalPrice(){
        return this.orderPrice * this.count; // 해당 주문 상품의 가격과 주문 수량을 곱한 총 가격 계산
    }
}
