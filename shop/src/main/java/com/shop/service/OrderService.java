package com.shop.service;

import com.shop.dto.OrderDTO;
import com.shop.dto.OrderHistDTO;
import com.shop.dto.OrderItemDTO;
import com.shop.entity.*;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

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
    private final ItemImgRepository itemImgRepository;

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

    // 사용자 아이디(여기선 이메일)와 Pageable 객체로 주문 리스트 페이징화 해서 받기
    @Transactional(readOnly = true) // 이것도 조회이므로 읽기 전용 트랜잭션
    public Page<OrderHistDTO> getOrderList(String email, Pageable pageable){
        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDTO> orderHistDTOs = new ArrayList<>();

        // 해당 사용자의 주문을 하나씩 순회함
        for(Order order : orders){
            OrderHistDTO orderHistDTO = new OrderHistDTO(order);
            List<OrderItem> orderItems = order.getOrderItems();

            // 주문의 주문 상품을 하나씩 순회함
            for(OrderItem orderItem : orderItems){
                // 주문 상품의 대표 이미지를 조회함
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepImgYn(orderItem.getItem().getId(), "Y"); // 해당 주문의 아이템의 ID, "Y"로 대표 이미지 엔티티 조회

                // 주문 데이터를 화면에 전송해서 써야 하므로 OrderItemDTO를 생성 후 생성자로 주문 상품 엔티티와 이미지 경로를 받아 생성함
                OrderItemDTO orderItemDTO = new OrderItemDTO(orderItem, itemImg.getImgUrl());

                // addOrderItemDTO()라는 메소드를 OrderHistDTO 안에 만들었었음 (안에 orderItemDTOList 있음)
                // OrderHistDTO 안의 orderItemDTOList에 추가
                orderHistDTO.addOrderItemDTO(orderItemDTO); // 주문 정보 DTO 내의 주문 상품 DTO 리스트에 추가
            }
            
            orderHistDTOs.add(orderHistDTO); // 주문 정보 리스트에 주문 정보 DTO 추가
        }

        // 페이지 구현 객체를 생성하여 반환함
        return new PageImpl<OrderHistDTO>(orderHistDTOs, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    // 자바스크립트는 조작이 가능하므로 주문 취소에 앞서 서버단에서 주문 번호와 사용자 이메일을 이용해 해당 사용자의 주문이 맞는지 검증한다
    public boolean validateOrder(Long orderId, String email){
        Member curMember = memberRepository.findByEmail(email); // 이메일로 실제 사용자를 DB에서 조회
        Order order = orderRepository.findById(orderId) // 주문 번호로 실제 주문을 DB에서 조회
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember(); // 조회한 주문의 Member 엔티티를 조회

        // 주문한 회원과 현재 로그인한 회원의 이메일이 일치하는지 검증
        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false; // 같지 않으면 false 리턴
        }
        
        return true; // 같으면 true 리턴
    }

    // 주문 취소 메소드
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        order.cancelOrder();
    }

    // 여러 개 한 번에 주문하는 메소드
    // 주문 정보(OrderDTO) 리스트, email을 매개변수로 받는다
    public Long orders(List<OrderDTO> orderDTOList, String email){

        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>(); // 주문 상품 리스트

        // 주문 정보를 순회하면서 주문 상품 엔티티를 만듦
        for(OrderDTO orderDTO : orderDTOList){
            Item item = itemRepository.findById(orderDTO.getItemId())
                    .orElseThrow(EntityNotFoundException::new);

            // 주문 상품 엔티티를 생성한다
            OrderItem orderItem = OrderItem.createOrderItem(item, orderDTO.getCount());
            orderItemList.add(orderItem); // 생성한 주문 상품 엔티티를 주문 상품 리스트에 추가
        }

        // 로그인한 회원과 주문 상품 목록을 이용해 주문 엔티티를 만듦
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order); // DB에 반영

        return order.getId();
    }
}









