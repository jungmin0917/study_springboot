package com.example.ex03.service;

import com.example.ex03.domain.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ServiceTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void orderTest(){
        OrderVO orderVO = new OrderVO();

        orderVO.setProductId(1L);
        orderVO.setProductCount(5L);

        orderService.order(orderVO);

        // 위와 같이 테스트하면 1번 상품의 ProductCount가 5 차감되는 것을 확인해야 한다
    }
}






