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
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne // 회원 당 주문이 여러 개 있으므로 다대일임
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태

    // 주문에서 주문 상품을 매핑함 (일대다 매핑이며, 관계의 주인이 아님)
    @OneToMany(mappedBy = "order") // 해당 관계의 주인이 아니며, 상대 엔티티의 order 필드에 의해 관리된다는 뜻 (상대 엔티티의 order라는 필드가 FK로 작동하고 있다는 의미임)
    private List<OrderItem> orderItems = new ArrayList<>(); // 하나의 주문이 여러 개의 주문 상품을 가지므로 List 자료형을 사용해서 매핑을 한다.

    private LocalDateTime regTime;
    private LocalDateTime updateTime;


}