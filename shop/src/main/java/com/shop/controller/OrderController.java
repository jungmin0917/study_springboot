package com.shop.controller;


import com.shop.dto.OrderDTO;
import com.shop.dto.OrderHistDTO;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/order")
    @ResponseBody // 반환할 값을 HTTP 응답의 body로 전달 (REST 방식)
    // @RequestBody : HTTP 요청의 본문(body)에 포함된 데이터를 자바 객체로 변환하기 위한 어노테이션.
    // Principal 객체는 스프링 시큐리티에서 인증된 사용자 정보를 제공하는 인터페이스이다. 로그인한 사용자의 정보를 가져오기 위해 사용한다.
    public ResponseEntity order (@RequestBody @Valid OrderDTO orderDTO, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder(); // StringBuilder 객체 생성

            List<FieldError> fieldErrors = bindingResult.getFieldErrors(); // 모든 필드에 대한 에러를 List로 저장
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage()); // StringBuilder 객체에 더해줌
            }

            // StringBuilder를 String으로 변환하여 ResponseEntity 객체로 리턴.
            return new ResponseEntity(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        // 필드에 에러가 없다면, 로그인한 사용자의 정보를 가져오기 위해 Principal.getName으로 인증 정보를 가져옴.
        // 현재 프로젝트에서는 .usernameParameter("email")라고 작성했으므로 이메일을 가져올 것이다.
        String email = principal.getName();
        Long orderId; // 주문 ID

        try{
            orderId = orderService.order(orderDTO, email); // OrderDTO 객체, 이메일을 이용하여 Order 엔티티 생성 및 DB 반영
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // 정상적으로 주문이 완료됐다면 생성된 주문 번호와 요청이 성공했다는 HTTP 응답 상태 코드를 반환한다.
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @GetMapping(value = {"/orders", "/orders/{page}"}) // 페이지가 있거나 없거나 매핑함
    // Optional 객체로 받음으로써 page가 null인 경우에도 상관 없음
    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4); // 한 페이지에 4개씩 출력

        // 현재 로그인한 회원의 이메일과 페이징 객체를 전달하여 주문 목록 DTO를 반환받음.
        Page<OrderHistDTO> ordersHistDtoList = orderService.getOrderList(principal.getName(), pageable);

        model.addAttribute("orders", ordersHistDtoList);
        model.addAttribute("page", pageable.getPageNumber()); // 현재 페이지 넘김
        model.addAttribute("maxPage", 5);

        return "order/orderHist";
    }
}










