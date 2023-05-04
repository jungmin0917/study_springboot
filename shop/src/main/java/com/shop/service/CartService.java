package com.shop.service;

import com.shop.dto.CartItemDTO;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // 장바구니 상품 DTO와 주문자 이메일을 이용하여 장바구니에 상품 추가
    public Long addCart(CartItemDTO cartItemDTO, String email){
        Item item = itemRepository.findById(cartItemDTO.getItemId()) // 상품 DTO의 상품 ID로 상품 엔티티 조회
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email); // 사용자 조회

        Cart cart = cartRepository.findByMemberId(member.getId()); // 사용자 장바구니 조회
        if(cart == null){ // 아직 사용자 장바구니가 없는 상태라면
            cart = Cart.createCart(member);
            cartRepository.save(cart); // DB에 사용자 장바구니 추가
        }

        // 현재 상품이 장바구니에 이미 들어가 있는지 확인한다.
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if(savedCartItem != null){ // 이미 장바구니에 해당 상품이 있으면
            savedCartItem.addCount(cartItemDTO.getCount()); // 추가한 개수만큼 기존 개수에 더한다
            return savedCartItem.getId(); // 장바구니 상품 ID 반환
        }else{
            // 장바구니에 해당 상품이 없으면 일단 만들어야 함
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDTO.getCount());
            cartItemRepository.save(cartItem); // DB에 장바구니 상품 추가
            return cartItem.getId(); // 장바구니 상품 ID 반환
        }
    }
}









