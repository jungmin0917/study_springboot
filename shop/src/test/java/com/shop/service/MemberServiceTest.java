package com.shop.service;

import com.shop.dto.MemberFormDTO;
import com.shop.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*; // Assertions 클래스를 편하게 쓰기 위해 스태틱 임포트

@SpringBootTest
@Slf4j
@Transactional // 테스트 클래스에 @Transactional 어노테이션을 선언할 경우, 테스트 실행 후 롤백 처리가 된다. 이를 통해 같은 메소드를 반복적으로 테스트할 수 있다.
@TestPropertySource(locations="classpath:application-test.properties") // 테스트용 설정 파일 지정
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder; // 여기서 주입받아서 생성된 객체를 createMember 메소드에 써먹음

    public Member createMember(){ // 회원가입용 전달 객체인 DTO 만드는 메소드
        MemberFormDTO memberFormDTO = new MemberFormDTO();
        memberFormDTO.setName("홍길동");
        memberFormDTO.setEmail("test@email.com");
        memberFormDTO.setPassword("1234"); // 여기서 Encode하지 않고 Member.createMember() 스태틱 메소드를 사용한다.
        memberFormDTO.setAddress("서울시 마포구 합정동");

        return Member.createMember(memberFormDTO, passwordEncoder); // 만든 DTO 객체로 엔티티 객체 생성
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){
        Member member = this.createMember(); // 엔티티 객체 생성
        Member savedMember = memberService.saveMember(member); // 생성된 엔티티 객체를 save() 한다
        // 여기서 save()를 한다는 건, 전달받은 엔티티의 상태에 따라 영속성 컨텍스트에 저장하거나 데이터베이스에 반영한다는 것을 의미한다.
        //

        // 아래 assertEquals 메소드로, 저장하려고 요청했던 값과 실제 저장된 데이터를 비교 검증한다.
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getRole(), savedMember.getRole());

        // 요청한 값(엔티티)과 저장된 값(컨텍스트에 저장된 엔티티)을 비교함으로써 회원가입이 정상적으로 성공했는지를 확인할 수가 있다.
    }
}









