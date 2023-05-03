package com.shop.service;

import com.shop.dto.OrderDTO;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.entity.Order;
import com.shop.entity.OrderItem;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    // 매개변수로 OrderDTO, 이메일을 받아 각 아이템과 주문자 아이디를 찾아 OrderItem 엔티티 및 Order 엔티티를 생성 후 DB에 반영한다. 그리고 방금 추가한 order의 orderId 값을 반환받는다.
    public Long order(OrderDTO orderDTO, String email){
        // 주문할 상품 조회
        Item item = itemRepository.findById(orderDTO.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        // 이메일 정보를 이용해 회원 정보 조회
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        // 주문할 상품 엔티티와 주문 수량을 이용해 주문 상품 엔티티 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDTO.getCount());
        orderItemList.add(orderItem); // 주문 상품 List에 추가

        Order order = Order.createOrder(member, orderItemList); // Order 엔티티 생성
        orderRepository.save(order); // DB에 반영

        return order.getId(); // orderId 반환
    }

}
