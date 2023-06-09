package com.shop.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="cart_item")
@Data
public class CartItem extends BaseEntity{

    @Id
    @GeneratedValue // strategy 속성 생략 시 기본값으로 AUTO가 설정됨
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 장바구니 상품 - 장바구니 간 다대일 매핑을 한다
    @JoinColumn(name = "cart_id")
    private Cart cart; // 매핑이므로 매핑 상대의 엔티티를 선언한다.

    @ManyToOne(fetch = FetchType.LAZY) // 장바구니 상품 - 상품 간 다대일 매핑을 한다
    @JoinColumn(name = "item_id")
    private Item item; // JPA 완전 초기에 만들었던 Item 엔티티 선언

    private Integer count; // 같은 상품을 장바구니에 몇 개 담았는지

    // 장바구니에 담을 장바구니 상품 엔티티를 생성하는 메소드
    public static CartItem createCartItem(Cart cart, Item item, int count){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);

        return cartItem;
    }

    // 장바구니에 담은 상품 수량을 증가시켜 줄 메소드
    public void addCount(int count){
        this.count += count;
    }

    // 장바구니에 담은 상품 수량을 변경해 줄 메소드
    public void updateCount(int count){
        this.count = count;
    }

}
