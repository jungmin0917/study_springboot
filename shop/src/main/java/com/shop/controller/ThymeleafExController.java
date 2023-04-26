package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/thymeleaf/*")
public class ThymeleafExController {

    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model){
        model.addAttribute("data", "타임리프 예제입니다.");

        return "thymeleafEx/thymeleafEx01"; // ViewResolver로 가서 앞뒤에 prefix, suffix 붙여준다
        // resources/templates 기준으로 뒤에 .html을 붙여서 찾아간다
    }

}
