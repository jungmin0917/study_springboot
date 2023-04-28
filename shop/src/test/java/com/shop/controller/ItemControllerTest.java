package com.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc; // 테스트 객체 주입받음

    @Test
    @DisplayName("상품 등록 페이지 권한 테스트")
    @WithMockUser(username = "admin", roles = "USER") // 현재 회원명이 admin이고, role이 ADMIN인 유저가 로그인 된 상태로 테스트할 수 있게 해주는 어노테이션
    public void itemFormTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) // 가상의 GET 요청 객체를 생성
                .andDo(print()) // 요청과 응답 메시지를 콘솔창에 출력해줌
                .andExpect(status().isForbidden()); // 요청과 응답 상태가 정상인지 확인함.
    }
}
