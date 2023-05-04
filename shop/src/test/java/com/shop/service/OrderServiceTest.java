package com.shop.service;

import com.shop.constant.ItemSellStatus;
import com.shop.constant.OrderStatus;
import com.shop.dto.OrderDTO;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.entity.Order;
import com.shop.entity.OrderItem;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Item saveItem(){ // 테스트를 위해 Item 엔티티를 만들어 컨텍스트에 저장하는 함수
        Item item = new Item();

        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);

        return itemRepository.save(item);
    }

    public Member saveMember(){ // 테스트를 위해 Member 엔티티를 만들어 컨텍스트에 저장하는 함수
        Member member = new Member();

        member.setEmail("test@test.com");

        return memberRepository.save(member);
    }

    @Test
    @DisplayName("주문 테스트")
    public void order(){
        Item item = this.saveItem();
        Member member = this.saveMember();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setItemId(item.getId()); // 아까 만든 상품의 itemId를 넣음
        orderDTO.setCount(10);

        // 주문 로직을 호출하고 생성된 주문 번호를 orderId에 저장
        Long orderId = orderService.order(orderDTO, member.getEmail());

        Order order = orderRepository.findById(orderId) // 방금 생성해서 DB에 넣은 주문을 주문번호로 조회한다.
                .orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems(); // 방금 만든 주문에서 orderItems를 가져온다

        int totalPrice = orderDTO.getCount() * item.getPrice(); // 주문 상품의 총 가격을 구한다.

        assertEquals(totalPrice, order.getTotalPrice()); // 계산한 주문 상품의 총 가격과 DB에 저장된 주문 상품이 가격이 같은지 확인하여 주문 로직이 잘 실행됐는지 확인한다.
    }

    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrder(){
        Item item = this.saveItem(); // 테스트 상품 생성
        Member member = this.saveMember(); // 테스트 회원 생성

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setItemId(item.getId());
        orderDTO.setCount(10);

        Long orderId = orderService.order(orderDTO, member.getEmail()); // 주문 데이터 생성

        Order order = orderRepository.findById(orderId) // 방금 생성한 주문을 DB에서 조회
                .orElseThrow(EntityNotFoundException::new);
        orderService.cancelOrder(orderId); // 방금 생성한 주문 취소함

        assertEquals(OrderStatus.CANCEL, order.getOrderStatus()); // 주문 취소 후 주문 상태가 취소 상태인지 확인
        assertEquals(100, item.getStockNumber()); // 주문 취소 후 테스트 상품의 재고가 처음과 같은지 확인
    }

}












