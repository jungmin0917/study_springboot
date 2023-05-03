package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 회원 당 주문이 여러 개 있으므로 다대일임
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태

    // 주문에서 주문 상품을 매핑함 (일대다 매핑이며, 관계의 주인이 아님)
    // 매핑 속성으로 cascade ALL 속성을 줌으로써 부모의 영속성 상태 변화를 자식 엔티티에 전부 전이함
    // 여기서 전부 전이한다는 건 persist, merge, remove, refresh, detach 속성을 전부 쓸 거라는 뜻이다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // 해당 관계의 주인이 아니며, 상대 엔티티의 order 필드에 의해 관리된다는 뜻 (상대 엔티티의 order라는 필드가 FK로 작동하고 있다는 의미임)
    private List<OrderItem> orderItems = new ArrayList<>(); // 하나의 주문이 여러 개의 주문 상품을 가지므로 List 자료형을 사용해서 매핑을 한다.

//    공통으로 상속받았음
//    private LocalDateTime regTime;
//    private LocalDateTime updateTime;

    // 주문 상품 엔티티를 매개변수로 받아 orderItems 리스트에 추가함
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this); // orderItem 엔티티 쪽에서도 다대일 매핑으로 Order 엔티티를 갖고있기에 해당 엔티티에 현재 주문 엔티티를 설정한다. (즉, 양방향 참조 관계라는 뜻)
    }

    // 매개변수로 Member 엔티티와 OrderItem 엔티티를 갖고 있는 리스트를 받아 Order 엔티티 반환하는 메소드
    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order(); // Order 엔티티를 반환하기 위해 Order 엔티티 객체 생성
        order.setMember(member);
        // 생성한 Order 엔티티 객체에 주문 상품을 전부 추가
        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER); // 주문 상태 설정. ORDER랑 CANCEL이 있음
        order.setOrderDate(LocalDateTime.now()); // 주문 시간 현재로 설정.
        return order; // 생성한 Order 엔티티 반환
    }

    // 총 주문 금액을 구하는 메소드
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : this.orderItems){ // 현재 주문에 담긴 주문 상품 엔티티를 순회하면서 참조
            totalPrice += orderItem.getTotalPrice(); // 상품 가격 * 주문 개수를 구하는 getTotalPrice를 주문 총 금액에 더한다.
        }

        return totalPrice;
    }

}
