package com.example.ex04.controller;

import com.example.ex04.domain.ProductVO;
import com.example.ex04.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/product/*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("list")
    public String list(Model model){
        model.addAttribute("products", productService.getList());
        model.addAttribute("productForm", new ProductVO()); // id, name 생성용

        return "product";
    }

    // ViewResolver를 거치지 않고 요청한 클라이언트에게 데이터를 반환함
    @ResponseBody // 페이지 이동이 아닌 데이터만을 반환함
    @PostMapping("new") // 웬만하면 동사가 아닌 명사로 URL를 설계하자
    public void register(@RequestBody ProductVO productVO){
        // 위와 같이 작성하면, registration URL로 JSON 요청이 들어오면, 해당 JSON의 키값과 ProductVO 필드를 스프링이 자동으로 매핑시켜준다.

        productService.register(productVO); // 상품 등록
    }

}













