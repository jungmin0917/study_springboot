package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderItemRepository;
import com.shop.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class OrderTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // OrderItem 엔티티는 Order 엔티티와 Item 엔티티를 모두 참조함

    @PersistenceContext
    private EntityManager em; // flush(), clear() 등 영속성 컨텍스트를 직접 다루기 위해

    public Item createItem(){ // 아이템 여러 개를 만들어서 Order 엔티티의 orderItems 필드에 넣기 위함임
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setStockNumber(100);
        item.setItemDetail("상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        return item;
    }

    public Order createOrder(){
        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setOrder(order);
            orderItem.setOrderPrice(1000);
            orderItem.setCount(10);

            order.getOrderItems().add(orderItem);
        }

        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }
    
    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){

        // 지금 테스트할 것은, Order 엔티티에 orderItems

        Order order = new Order(); // OrderItem 엔티티 만들 때 Order랑 Item 엔티티 참조해야 하므로 만듦

        for (int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item); // 아이템을 영속성에 저장함

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setOrderPrice(1000);

            order.getOrderItems().add(orderItem); // set으로 안 하고 get한 다음에 리스트에 추가하는 듯
        }

        orderRepository.saveAndFlush(order); // order 엔티티 저장 후 DB에 반영함
        em.clear(); // 영속성 컨텍스트 비움

        Order savedOrder = orderRepository.findById(order.getId()) // 아까 만들었던 order를 조회함
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size()); // order 조회하면서 orderItems 엔티티도 실제로 데이터베이스에 저장됐는지 검사한다.
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest(){
        Order order = this.createOrder(); // orderItem이 3개가 저장이 될 것임
        order.getOrderItems().remove(0); // 0번째 인덱스 지우기
        em.flush(); // DB에 반영하기
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest(){
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId(); // 첫 번째 orderItem의 id

        // DB에 반영 후 컨텍스트 비움
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId) // OrderItem만을 조회했음.
                .orElseThrow(EntityNotFoundException::new);
        System.out.println("Order class : " + orderItem.getOrder().getClass()); // OrderItem 엔티티에 있는 Order 객체의 클래스 출력해본다.
        System.out.println("============================");
        orderItem.getOrder().getOrderDate();
        System.out.println("============================");
    }
}








