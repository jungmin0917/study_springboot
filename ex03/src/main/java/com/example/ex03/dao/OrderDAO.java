package com.example.ex03.dao;

import com.example.ex03.domain.OrderDTO;
import com.example.ex03.domain.OrderVO;
import com.example.ex03.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

// DAO는 인터페이스 안 쓰고 바로 클래스로 구현하기로 함

@Repository
@RequiredArgsConstructor
public class OrderDAO {
    private final OrderMapper orderMapper;

//    주문하기
    public void save(OrderVO orderVO){
        orderMapper.insert(orderVO);
    }

//    주문 내역
    public List<OrderDTO> findAll(String sort){
        return orderMapper.selectAll(sort);
    }
}
