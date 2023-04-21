package com.example.ex02.controller;

import com.example.ex02.domain.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/product/*")
public class ProductController {
    @GetMapping("register")
    public String register(Model model){
        model.addAttribute("productVO", new ProductVO()); // 비어 있는 VO 객체를 전달한다
        return "/product/product-register";
    }
}
