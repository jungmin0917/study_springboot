package com.shop.service;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.CartItemDTO;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.repository.CartItemRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;

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
    @DisplayName("장바구니 담기 테스트")
    public void addCart(){
        // 테스트용 상품과 회원 엔티티 생성
        Item item = this.saveItem();
        Member member = this.saveMember();

        CartItemDTO cartItemDTO = new CartItemDTO(); // 장바구니 추가에 사용할 CartItemDTO 생성
        
        // 장바구니에 담을 상품과 수량을 cartItemDTO 객체에 세팅함
        cartItemDTO.setItemId(item.getId());
        cartItemDTO.setCount(5);

        Long cartItemId = cartService.addCart(cartItemDTO, member.getEmail()); // 장바구니에 추가

        CartItem cartItem = cartItemRepository.findById(cartItemId) // 장바구니에 추가한 상품을 조회
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(item.getId(), cartItem.getItem().getId()); // 조회한 장바구니 상품과 생성한 상품의 ID가 같은지 확인
        assertEquals(cartItemDTO.getCount(), cartItem.getCount()); // 추가하려는 상품 개수와 장바구니에 들어있는 상품의 개수가 같은지 확인
        
    }
}












