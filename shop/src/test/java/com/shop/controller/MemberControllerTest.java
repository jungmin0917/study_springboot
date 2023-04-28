package com.shop.controller;

import com.shop.dto.MemberFormDTO;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

// formLogin() 메소드를 쓰기 위해 SecurityMockMvcRequestBuilders를 스태틱화함
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc // MockMvc 테스트를 위해 선언
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc; // 실제 객체와 비슷하지만 테스트에 필요한 기능만 가지는 가짜 객체. 이 객체를 이용하면 웹 브라우저에서 요청을 하는 것처럼 테스트할 수 있다.

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member createMember(String email, String password){ // 이메일, 비밀번호 전달받아 회원 엔티티 만드는 메소드. 반환된 엔티티는 영속성 컨텍스트에 성공적으로 저장된 엔티티이다.
        MemberFormDTO memberFormDTO = new MemberFormDTO();
        memberFormDTO.setName("홍길동");
        memberFormDTO.setEmail(email);
        memberFormDTO.setPassword(password); // 여기서 Encode하지 않고 Member.createMember() 스태틱 메소드를 사용한다.
        memberFormDTO.setAddress("서울시 마포구 합정동");

        Member member = Member.createMember(memberFormDTO, passwordEncoder); // 만든 DTO 객체로 엔티티 객체 생성

        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception{
        String email = "test@test.com";
        String password = "1234";
        this.createMember(email, password); // 이메일, 비밀번호로 엔티티 만든다

        // 방금 가입한 회원정보로 로그인 테스트를 한다.
        mockMvc.perform(formLogin()
                .userParameter("email")
                .loginProcessingUrl("/members/login") // userParameter로 이메일을 아이디로 세팅하고 로그인 URL에 요청함
                .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated()); // 로그인이 성공하여 인증되었다면 테스트 코드 통과함
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception{

        String email = "test@test.com";
        String password = "1234";
        this.createMember(email, password); // 이메일, 비밀번호로 엔티티 만든다

        // 방금 가입한 회원정보로 로그인 테스트를 한다.
        mockMvc.perform(formLogin()
                        .userParameter("email")
                        .loginProcessingUrl("/members/login") // userParameter로 이메일을 아이디로 세팅하고 로그인 URL에 요청함
                        .user(email).password("12345")) // 틀린 패스워드 입력
                .andExpect(SecurityMockMvcResultMatchers.authenticated()); // 로그인이 성공하여 인증되었다면 테스트 코드 통과함
    }

}
