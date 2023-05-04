package com.shop.dto;

// 주문 정보를 담을 DTO

import com.shop.constant.OrderStatus;
import com.shop.entity.Order;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderHistDTO { // 주문 정보 DTO (지금은 아직 한 종류만 있지만 장바구니 만들면 여러 아이템이 들어갈 수 있음)

    private Long orderId; // 주문 번호
    private String orderDate; // 주문 날짜
    private OrderStatus orderStatus; // 주문 상태
    
    // 주문 상품 리스트
    private List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

    // 아래와 같이 굳이 엔티티를 DTO로 변환할 필요 없게끔 아예 Order 엔티티를 생성자 매개변수로 받아서 뭔갈 하는듯?
    public OrderHistDTO(Order order){
        // OrderHistDTO 클래스의 생성자로 Order 엔티티 객체를 파라미터로 받아 필드값을 세팅한다.
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // Order 엔티티 쪽에서는 orderDate 필드를 LocalDateTime 객체로 갖고 있기에 format() 메소드를 통해 String 클래스로 변환해준다.
        this.orderStatus = order.getOrderStatus();
    }

    public void addOrderItemDTO(OrderItemDTO orderItemDTO){
        orderItemDTOList.add(orderItemDTO); // 주문 상품 리스트에 orderItemDTO 객체 추가
    }

}
