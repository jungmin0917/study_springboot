package com.shop.service;

import com.shop.dto.CartDetailDTO;
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
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // 장바구니 상품 DTO와 사용자 이메일을 이용하여 장바구니에 상품 추가
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
    
    // 사용자 이메일로 장바구니 리스트 조회
    @Transactional(readOnly = true)
    public List<CartDetailDTO> getCartList(String email){
        List<CartDetailDTO> cartDetailDTOList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId()); // 아이디로 장바구니 조회

        // 만약 장바구니가 아직 없으면
        if(cart == null){
            // 따로 장바구니를 미리 만들진 않고 그냥 빈 list 객체를 반환함
            return cartDetailDTOList;
        }

        // 장바구니 상품 정보를 조회하여 담는다
        cartDetailDTOList = cartItemRepository.findCartDetailDTOList(cart.getId());

        return cartDetailDTOList;
    }

    // 현재 사용자와 변경할 장바구니 상품의 주인이 같은지 확인
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email){
        Member curMember = memberRepository.findByEmail(email); // 현재 회원 조회
        CartItem cartItem = cartItemRepository.findById(cartItemId) // 현재 장바구니 상품 조회
                .orElseThrow(EntityNotFoundException::new);

        Member savedMember = cartItem.getCart().getMember(); // 해당 상품이 담긴 장바구니의 주인 조회
        
        // 해당 상품의 주인과, 현재 회원이 다르면 false 리턴
        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    // 장바구니 상품 ID와 수량을 받아 장바구니 상품 수량을 업데이트
    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    // 장바구니 상품 ID를 받아 장바구니에서 삭제하는 메소드
    public void deleteCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItemRepository.delete(cartItem);
    }
}







