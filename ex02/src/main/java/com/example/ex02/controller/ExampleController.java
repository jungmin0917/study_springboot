package com.example.ex02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 스프링 관리 대상으로 만듦으로써 HandlerMapping이 이 클래스를 찾을 수 있게 된다.
@RequestMapping("/ex/*") // FrontController처럼, 요청의 겹치는 부분(상위 부분)을 적어줄 수 있다
public class ExampleController {
    // 여기 내부에는 /ex/~~ 처럼 하위의 부분만 적으면 된다 (컨트롤러 부분)
}
