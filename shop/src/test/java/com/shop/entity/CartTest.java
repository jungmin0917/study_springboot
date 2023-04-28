package com.shop.entity;

import com.shop.dto.MemberFormDTO;
import com.shop.repository.CartRepository;
import com.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals; // 검증용

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class CartTest {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em; // 직접 flush()나 clear()를 호출하여 엔티티를 즉시 관리하기 위해 주입받음

    public Member createMember(){ // member 엔티티 생성 메소드
        MemberFormDTO memberFormDTO = new MemberFormDTO();
        memberFormDTO.setName("홍길동");
        memberFormDTO.setEmail("test@test.com");
        memberFormDTO.setPassword("1234");
        memberFormDTO.setAddress("서울시 마포구 합정동");

        return Member.createMember(memberFormDTO, passwordEncoder);
    }
    
    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest(){
        Member member = this.createMember();
        memberRepository.save(member); // member 엔티티를 먼저 영속성 컨텍스트에 저장

        Cart cart = new Cart();
        cart.setMember(member); // Cart 엔티티에 member 엔티티를 필드로 선언해놓았다 (연관 관계 매핑용)
        cartRepository.save(cart); // cart 엔티티도 member 엔티티를 받아서 영속성 컨텍스트에 저장

        em.flush(); // 영속성 컨텍스트 상태를 DB에 반영
        em.clear(); // 영속성 컨텍스트를 비운다. 영속성 컨텍스트에 엔티티가 없을 때 해당 엔티티를 조회하면 실제 DB로부터 조회한다. 이제 cart 엔티티만 조회했을 때 member 엔티티도 같이 조회되는지 확인해보자.

        Cart savedCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new); // 엔티티를 검색 후, 검색 결과가 없으면 EntityNotFoundException을 반환함
        
        assertEquals(savedCart.getMember().getId(), member.getId()); // 생성했던 member의 id와 DB에서 가져온 Cart 엔티티의 Member 엔티티의 id가 같은지 검증한다.
        
    }


}










