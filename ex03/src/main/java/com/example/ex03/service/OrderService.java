package com.example.ex03.service;

// 원래 인터페이스 쓰지만 현재는 편의를 위해 그냥 클래스로 바로 구현한다

import com.example.ex03.dao.OrderDAO;
import com.example.ex03.domain.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 생성자 주입
public class OrderService {

    private final OrderDAO orderDAO;

//    주문하기
    public void order(OrderVO orderVO){
        orderDAO.save(orderVO);
    }
}
