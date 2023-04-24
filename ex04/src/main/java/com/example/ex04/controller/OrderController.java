package com.example.ex04.controller;


import com.example.ex04.domain.OrderDTO;
import com.example.ex04.domain.OrderVO;
import com.example.ex04.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order/*")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("list/{sort}")
    public List<OrderDTO> list(@PathVariable("sort") String sort){
        log.info("/order/list....................." + sort);

        if(sort == null){
            sort = "recent";
        }

        return orderService.getList(sort);
    }

    @PostMapping("new")
    public void register(@RequestBody OrderVO orderVO){
        orderService.order(orderVO);
    }
}













