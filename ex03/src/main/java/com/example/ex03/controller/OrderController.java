package com.example.ex03.controller;

import com.example.ex03.domain.OrderVO;
import com.example.ex03.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor // 생성자 주입
@RequestMapping("/order/*")
public class OrderController {
    private final OrderService orderService; // 생성자 주입

    @PostMapping("done") // /order/done으로 들어왔을 때
    public RedirectView order(OrderVO orderVO){ // 리다이렉트로 처리
        orderService.order(orderVO); // 주문 처리 후
        return new RedirectView("/product/list"); // list로 이동
        // 주문하고 다시 상품 목록 보여주기
        // 여기서 Redirect로 이동하는 이유는, 새로고침하지 말라는 것도 있음 (처리 후 request객체 지우려고)
    }
    
    @GetMapping("list")
    public String list(@RequestParam(required = false) String sort, Model model){
//        @RequestParam(required = false)는, 해당 파라미터의 값이 null값이어도 된다는 뜻
        if(sort == null){
            sort = "recent"; // sort의 기본값 설정
        }

        model.addAttribute("sort", sort);
        model.addAttribute("orders", orderService.getList(sort));

        return "/order/order-list";
    }
}







