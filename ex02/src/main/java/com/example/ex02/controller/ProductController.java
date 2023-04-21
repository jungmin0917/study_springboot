package com.example.ex02.controller;

import com.example.ex02.domain.ProductVO;
import com.example.ex02.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Controller
@RequestMapping("/product/*")
@RequiredArgsConstructor
public class ProductController {
    private final ProductMapper productMapper;

    @GetMapping("register")
    public String register(Model model){
        model.addAttribute("productVO", new ProductVO()); // 비어 있는 VO 객체를 전달한다
        return "/product/product-register";
    }

    @PostMapping("register")
    public RedirectView register(ProductVO productVO){
        productMapper.insert(productVO);
//      return "/product/product-list"; // 그냥 이렇게 써버리면 컨트롤러를 거치지 않고 바로 HTML파일로 가서 DB 조회가 안 된다.
        return new RedirectView("/product/list"); // 이렇게 적어서 이동하면 다른 컨트롤러로 이동한다
        // 그래서 Redirect를 사용해야 한다. 위와 같이 그냥 return으로 가는 건 Forward 방식이다.
        // 응답하는 방식에도 크게 두 가지 (Forward, Redirect)가 있는데,
        // Forward로 응답하면, 이전 요청에서 생성된 request 객체와 response 객체가 유지된다.
        // Forward: /a로 요청을 해서 b.jsp로 갔다면, /a URL이 그대로 남아있다. (객체도 남아 있고)
        // Redirect: /a로 요청을 해서 각종 처리를 하고 /b로 이동시킨다. request 객체와 response 객체가 초기화된다.
        
        // 즉, Redirect로 이동하면, HTML이 아닌 다른 컨트롤러를 찾는다.
        // 그러므로 이동할 컨트롤러 경로를 적어주면 된다

    }

    @GetMapping("list")
    public String list(Model model){
        model.addAttribute("products", productMapper.selectAll());

        return "/product/product-list";
    }
    
    // 파트1 끝
}










