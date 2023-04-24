package com.example.ex04.controller;

import com.example.ex04.domain.ProductVO;
import com.example.ex04.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/product/*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("list")
    public String list(Model model){
        model.addAttribute("products", productService.getList());
        ProductVO productVO = new ProductVO();
        model.addAttribute("productVO", productVO); // id, name 생성용

        return "product";
    }
}
