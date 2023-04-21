package com.example.ex02.controller;

import com.example.ex02.domain.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Slf4j

@Controller // 스프링 관리 대상으로 만듦으로써 HandlerMapping이 이 클래스를 찾을 수 있게 된다.
@RequestMapping("/ex/*") // FrontController처럼, 요청의 겹치는 부분(상위 부분)을 적어줄 수 있다
public class ExampleController {
    // 여기 내부에는 /ex/~~ 처럼 하위의 부분만 적으면 된다 (컨트롤러 부분)

    @RequestMapping(value = "ex01", method = RequestMethod.GET)
    public void ex01(){
        log.info("=============ex01============="); // 이 URL로 들어왔다는 것을 로깅
    }

    @GetMapping("ex02")
    public void ex02(HttpServletRequest request){
        log.info(request.getParameter("name"));
    }
    // 근데 위와 같이 사용하면 HttpServletRequest 객체를 직접 다루고 각종 메소드를 직접 다뤄야 하기 때문에 상당히 불편할 수 있다.
    
    @GetMapping("ex03")
    public void ex03(String name){
        log.info(name);
    }
    // 아주 단순하게 위와 같이 적어도 Request 값에 대한 매핑을 알아서 해준다. 그래서 name이라는 필드값으로 요청을 하면 같은 변수명으로 처리할 수 있다

    @GetMapping("ex04")
    public void ex04(MemberVO memberVO){
        log.info("member: " + memberVO.toString());
    }
    // 위 메소드에서 객체로 받으면, 그 객체의 필드명과 동일한 요청 필드를 알아서 매핑해준다.

    @GetMapping("ex05")
    public void ex05(@RequestParam("id") String name, @RequestParam("age") int age){
        log.info("name: " + name);
        log.info("age: " + age);
    }
    // 만약 이곳의 매개변수명과 요청으로 들어온 필드명이 다를 경우, 위와 같이 @Param 어노테이션을 이용하면 된다

    @GetMapping("ex06")
    public void ex06(MemberVO memberVO, String id){
        log.info("member's name: " + memberVO.getName());
        log.info("member's age: " + memberVO.getAge());
        log.info("name: " + id);
    }
    // 위에처럼 객체와 필드를 둘 다 같은 이름으로 받는다면? 여기서는 memberVO 안에도 name 필드가 있고, 또 name을 받고 있다.
    // 위와 같은 경우, 필드명과 객체의 필드명이 겹쳐서 둘 다 값이 들어가게 된다.
    // 뭐 id라고 바꾸고 따로 받으면 딱히 상관 없다 (이름이 겹치지 않게만 주의하면 됨)


    // 이제 화면으로 응답을 해 보자
    // 여기서의 메소드 반환값은 이동할 경로가 된다.
    // 별도로 반환값을 입력하지 않으면 요청이 들어온 그대로의 URL로 이동한다 (기본적으로 resources/templates에서 찾는다)
    @GetMapping("ex07")
    public void ex07(){
        
    }

}
