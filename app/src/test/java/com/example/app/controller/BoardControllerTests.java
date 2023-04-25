package com.example.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Slf4j
public class BoardControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

//    @BeforeAll은 static 메서드로 선언하고, 모든 테스트가 실행되기 전 딱 한 번 실행된다.
//    @BeforeEach는 각각의 테스트가 실행되기 전에 실행된다.

    @BeforeEach // 현재 클래스의 모든 단위테스트(@Test 어노테이션 붙은 메소드) 전에 이 메소드부터 실행하겠다는 것이다
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        // MockMvc 객체 생성 시 WebApplicationContext를 매개변수로 넣어주고 build해준다.

        // 브라우저를 사용하지 않고 MockMvc 객체를 사용하여 요청, 응답을 테스트해보자는 것이다.
    }

    @Test
    public void showListTest() throws Exception {
        // MockMvc.perform() : 브라우저를 열고 매개변수로 요청을 보냄
        // MockMvcRequestBuilders : 각종 요청 생성하는 객체. 메소드로 get(), post(), put(), patch(), delete() 등이 있다
        // 만약 요청 시 보내고 싶은 데이터가 있으면 해당 메소드(get, post 등) 뒤에 .param() 메소드로 설정하면 된다.
        // andReturn() : 주소창에 주소 다 적고 엔터를 쳤다고 보면 됨
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/board/list")) // 요청을 함 (주소 입력 및 파라미터 입력)
                .andReturn() // 엔터를 침
                .getModelAndView() // 반환된 ModelAndView 객체를 가져옴
                .getModelMap()
                .toString(); // getModelMap과 toString은 우리가 반환된 객체를 문자열로 보고 싶어서 변환함

        log.info(result);
}

    @Test
    public void getBoardTest() throws Exception{
//        String result = mockMvc.perform(MockMvcRequestBuilders.get("/board/read").param("boardId", "3"))
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/board/modify").param("boardId", "10"))
                .andReturn()
                .getModelAndView()
                .getModelMap()
                .toString();

        log.info(result);
    }

    @Test
    public void writeTest() throws Exception{
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/board/write")
                .param("boardTitle", "테스트 제목5")
                .param("boardWriter", "테스트 작성자5")
                .param("boardContent", "테스트 내용5"))
                .andReturn()
                .getFlashMap() // addFlashAttribute로 넘어온 값을 가져옴
                .toString();

        log.info(result);
    }

    @Test
    public void removeTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/board/remove").param("boardId", "11"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        // andExpect(): 요청에 대한 검증을 하는 메소드
        // 위처럼 작성하면 해당 요청에 대한 응답이 리다이렉트인지 확인하는 것이다
    }

    @Test
    public void modifyTest() throws Exception{
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
                .param("boardId", "10")
                .param("boardTitle", "수정된 제목")
                .param("boardContent", "수정된 내용"))
                .andReturn()
                .getModelAndView()
                .getModelMap()
                .toString();

        log.info(result);
    }

}













